package legacy.hedge;

import java.math.*;
import java.util.*;
import java.util.logging.*;

import legacy.dates.*;
import legacy.dto.*;
import legacy.error.*;
import legacy.persistence.*;
import legacy.security.*;
import legacy.service.*;

/**
 * <p>
 * Title: legacy.hedge.HPManager
 * </p>
 * <p/>
 * Description: This is the main calculation "hub" of the Polka Application, this
 * class handle all sort of different type of HEdgingPosition, and calculate their
 * values according to the proper values.
 * </P>
 *
 * @author cBrown
 * @version 17.3b4 (revision b)
 * @creationDate October 17, 2012
 */
public class HedgingPositionManager {
  private static final Logger LOGGER = Logger.getLogger(HedgingPositionManager.class.getName());

  private final ITransactionManagerService transactionManager = DataAccessService.getTransactionManagerService();

  public CheckResult<HedgingPosition> initAndSendHedgingPosition(HedgingPosition hp) {
    CheckResult<HedgingPosition> result = new CheckResult<HedgingPosition>();
    try {
      hp = initHedgingPosition(hp);
    } catch (Exception e) {
      String errorMsg = "TECHNICAL ERROR, cannot initialize HP to send";
      LOGGER.log(Level.SEVERE, errorMsg, e);
      String msg = hp.getErrorLevel().createHMsgFromError();
      hp.setHedgeMsg(msg);
      result.setCheckIsOk(false);
      try {
        updateHedgingPosition(hp);
      } catch (Exception e1) {
        LOGGER.log(Level.SEVERE, errorMsg, e1);
      }
      return result;
    }
    try {
      result = hedgePositionBySendTo3rdParty(hp);
      if (result.isCheckIsOk()) {
        hp = result.getResult();
        hp.setStatus(HedgingPositionStatus.HEDGED);
        updateHedgingPosition(hp);
      } else {
        hp = result.getResult();
        hp.setStatus(HedgingPositionStatus.REJECTED);
        updateHedgingPosition(hp);
      }
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
    }
    return result;
  }

  private CheckResult<HedgingPosition> hedgePositionBySendTo3rdParty(HedgingPosition hp) {
    if (LOGGER.isLoggable(Level.FINEST)) {
      LOGGER.log(Level.FINEST, "Begin 3r party processing. stand by");
    }
    CheckResult<HedgingPosition> result;
    result = HedgingPositionSender.sendToTowering(hp);
    if (LOGGER.isLoggable(Level.FINEST)) {
      LOGGER.log(Level.FINEST, "3r party processing is now finished, thank you for your patience"); // t'es con michel
    }
    return result;
  }

  private HedgingPosition updateHedgingPosition(HedgingPosition hp) {
    HedgingPosition hpUpdate = hp.copy();
    try {
      if (hp.getType().equals(HedgingPositionType.INI)) {
        hpUpdate.setTransactionId(hp.getTransactionId());
        Modif modif = new Modif();
        modif.setModificationDate(new Date());
        hp.setLastModification(modif);
        hp.setStorageUpdate(StorageActionEnum.CREATE);
        hpUpdate = transactionManager.classStorageAction(hp);
      } else {
        hp.setStorageUpdate(StorageActionEnum.UPDATE);
        hpUpdate = transactionManager.classStorageAction(hp);
      }
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
      throw new RuntimeException(e);
    }
    return hpUpdate;
  }

  private HedgingPosition initHedgingPosition(HedgingPosition hp) {
    ITradingDataAccessService trading = DataAccessService.getTradingDataAccessService();
    IHedgingPositionDataAccessService hpdas = DataAccessService.getHedgingPositionDataAccessService();
    Transaction transaction = trading.getTransactionById(hp.getId());
    long dId = trading.getOptionalIdFromTransaction(transaction);

    double price = hpdas.getPriceQuote(dId, transaction);
    long dps = trading.computeDPSOnTheGrid(transaction.getOuterEdge());
    String combck = dId + " " + transaction.getId() + " CONTROL: [" + hpdas.getControl() + "]";
    Date valueDate = new Date();
    try {
      valueDate = hp.getValueDate();
    } catch (Exception e) {
      valueDate = transaction.getValueDate();
    }

    String hedgingTransactionId = new String();
    if (!HedgingPositionType.INI.equals(hp.getType())) {
      hedgingTransactionId = hpdas.getHedgingTransactionIdByTransactionId(transaction.getId());
    }
    String userIni = getUser();
    hp.setIkRtH(userIni);
    switch (hp.getType()) {
      case INI:
        String transactionWay = new String();
        switch (transaction.getWay()) {
          case LONG:
            transactionWay = "L";
            break;
          case SHORT:
            transactionWay = "S";
            break;
          default:
            break;
        }
        int bodCode = 0;
        Integer stock = DataAccessService.getAnalyticalService().getRetrieveStockByActiveGK(transaction.getId(), transactionWay);
        TradingOrder evt1 = hpdas.getTrade(transaction.getId());
        boolean isStockForbidden = false;
        if (stock == null) {
          isStockForbidden = true;
        }
        if (!isStockForbidden) {
          Book book = DataAccessService.getAnalyticalService().getBookByName(transaction.getBookName());
          bodCode = book.getCode();
        } else {
          Book book = DataAccessService.getAnalyticalService().getBookByName(transaction.getBookName() + "-instock");
          bodCode = Integer.parseInt(book.getPortfolioIdFromRank());
        }
        /*********************************** INPUT DEAL DATA *********************/
        hp.setTransactionWay(transactionWay);
        hp.setCodetyptkt(34);
        hp.setCodtyptra(BigInteger.valueOf(bodCode));
        hp.setQuantity(String.valueOf(evt1.getPrice().getQuantity()));
        hp.setBasprx(evt1.getPrice().getFxPrice() / 100);
        hp.setPrxref(evt1.getPrice().getFxPrice());
        hp.setCombck(combck);
        /*********************************** INPUT EVENT DATA *********************/
        hp.setTransactionId(transaction.getId());
        hp.setValueDate(valueDate);
        break;
      case CANCEL_TRANSACTION:
        /*********************************** INPUT DEAL DATA *********************/
        String hedgingPositionId = hpdas.getHedgingPositionIdByPositionKey(transaction.getPositionKey());

        hp.setCodetyptkt(20);
        /*********************************** INPUT EVENT DATA *********************/
        hp.setValueDate(valueDate);
        break;
      case EXT:
        TradingOrder evt = hpdas.getTrade(transaction.getId());
        double fxprice = -1d;
        if (evt != null) {
          price = evt.getPrice().getPrice();
          fxprice = evt.getPrice().getFxPrice();
        }
        if (price > 0) {
          price = price * fxprice;
        }
        /*********************************** INPUT DEAL DATA *********************/
        hp.setBasprx(price / 100);
        hp.setPrxref(price);
        hp.setCodetyptkt(42);
        hp.setQuantity(String.valueOf(evt.getPrice().getQuantity()));
        /*********************************** INPUT EVENT DATA *********************/
        Date issueDate = transaction.getIssueDate();
        Date tradeDate = transaction.getTradeDate();
        if (DateTimeUtils.compareDate(issueDate, tradeDate)) {
          hp.setCreDate(issueDate);
          hp.setDaprx(tradeDate);
          hp.setDatefinthe(valueDate);
        } else {
          hp.setCreDate(issueDate);
          hp.setDaprx(tradeDate);
          hp.setDatefinthe(tradeDate);
          hp.setCombck(combck);
        }
        hp.setValueDate(valueDate);
        break;
      case CANCEL_POSITION:
        /*********************************** INPUT DEAL DATA *********************/
        hp.setCodetyptkt(20);
        hp.setHedgingTransactionId(hedgingTransactionId);
        /*********************************** INPUT EVENT DATA *********************/
        hp.setValueDate(valueDate);
        break;
    }

    return hp;
  }

  private String getUser() {
    User user = UserSessionsManager.getInstance().getCurrentUser();
    return user == null ? "autobot" : user.getName();
  }
}
