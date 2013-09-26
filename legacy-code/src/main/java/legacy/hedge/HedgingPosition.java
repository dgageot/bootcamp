package legacy.hedge;

import java.math.*;
import java.util.*;

import legacy.dto.*;
import legacy.persistence.*;
import legacy.service.*;

import org.apache.commons.lang3.*;

/**
 * <p>
 * Title: legacy.hedge.HedgingPosition
 * </p>
 * <p/>
 * Description: bug, n: A son of a glitch.
 * </P>
 *
 * @author mfugain
 * @version 10
 * @creationDate May 12, 2007
 */
public class HedgingPosition extends BaseDTO implements Position {

  /**
   * **************** Data required for processing************************
   */
  @AuditedField
  private int transactionId;
  @AuditedField
  private HedgingPositionType type = HedgingPositionType.INI;
  @AuditedField
  private HedgingPositionStatus status;
  @AuditedField
  private Date valueDate;
  @AuditedField
  private String combck;
  @AuditedField
  private int codetyptkt;

  private String transactionWay;

  //private int assetCopy;
  private String hedgeMsg;

  private StorageActionEnum storageAction;

  // Not Stored in database but use in Position processing order form 3
  private double prxref;

  private double basprx = 100;
  private Date daprx;
  /**
   * ***************** Input Valuation Data ********************
   */
  private String quantity;

  /**
   * **************** Inpupt Front data *******************
   */
  private Date datefinthe;

  private BigInteger codtyptra = new BigInteger("42");
  private String ikRtH;
  private String hedgingTransactionId;

  /**
   * HedgingPosition Constructor
   */
  public HedgingPosition() {

  }

  /**
   * @return the transactionId
   */
  @Override
  public int getTransactionId() {
    return transactionId;
  }

  /**
   * @param transactionId the transactionId to set
   */
  public void setTransactionId(int transactionId) {
    this.transactionId = transactionId;
  }

  /**
   * @return type
   */
  public HedgingPositionType getType() {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType(HedgingPositionType type) {
    this.type = type;
  }

  public HedgingPositionStatus getStatus() {
    return status;
  }

  public void setStatus(HedgingPositionStatus status) {
    this.status = status;
  }

  @Override
  public Date getValueDate() {
    return valueDate;
  }

  public void setValueDate(Date valueDate) {
    this.valueDate = valueDate;
  }

  @Override
  public Date getNoticePeriodEndDate() {
    return null;
  }

  // TODO: Rename this to CombockArray
  @Override
  public String getCombck() {
    return combck;
  }

  public void setCombck(String combck) {
    this.combck = combck;
  }

  @Override
  public BigInteger getCodtyptra() {
    return codtyptra;
  }

  public void setCodtyptra(BigInteger codtyptra) {
    this.codtyptra = codtyptra;
  }

  @Override
  public int getCodetyptkt() {
    return codetyptkt;
  }

  public void setCodetyptkt(int codetyptkt) {
    this.codetyptkt = codetyptkt;
  }

  @Override
  public double getPrxref() {
    return prxref;
  }

  public void setPrxref(double prxref) {
    this.prxref = prxref;
  }

  @Override
  public double getBasprx() {
    return basprx;
  }

  public void setBasprx(double basprx) {
    this.basprx = basprx;
  }

  @Override
  public Date getDaprx() {
    return daprx;
  }

  public void setDaprx(Date daprx) {
    this.daprx = daprx;
  }

  @Override
  public String getQuantity() {
    return quantity;
  }

  // FIXME: Quantity should be an integer
  public void setQuantity(String quantity) {
    this.quantity = quantity;
  }

  @Override
  public String getTransactionWay() {
    return transactionWay;
  }

  public void setTransactionWay(String transactionWay) {
    this.transactionWay = transactionWay;
  }

  @Override
  public String getMsgdev() {
    return getMsgusr();
  }

  @Override
  public String getMsgerr() {
    return "FATAL ERROR";
  }

  @Override
  public Integer getNiverr() {
    return 0;
  }

  @Override
  public String getMsgusr() {
    return "Hedging position";
  }

  @Override
  public ErrorLevel getErrorLevel() {
    return ErrorLevel.FATAL_ERROR;
  }

  @Override
  public String getHedgeMsg() {
    return hedgeMsg;
  }

  public void setHedgeMsg(String hedgeMsg) {
    this.hedgeMsg = hedgeMsg;
  }

  @Override
  public Date getDatefinthe() {
    return datefinthe;
  }

  public void setDatefinthe(Date datefinthe) {
    this.datefinthe = datefinthe;
  }

  @Override
  public StorageActionEnum getStorageUpdate() {
    return storageAction;
  }

  public void setStorageUpdate(StorageActionEnum storageAction) {
    this.storageAction = storageAction;
  }

  public void setIkRtH(String ikRtH) {
    this.ikRtH = ikRtH;
  }

  @Override
  public String getIkRtH() {
    return ikRtH;
  }

  public void setHedgingTransactionId(String hedgingTransactionId) {
    this.hedgingTransactionId = hedgingTransactionId;
  }

  @Override
  public String getHedgingTransactionId() {
    return hedgingTransactionId;
  }

  public HedgingPosition copy() {
    return SerializationUtils.clone(this);
  }
}
