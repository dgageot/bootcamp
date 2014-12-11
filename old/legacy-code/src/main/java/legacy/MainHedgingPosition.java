package legacy;

import java.util.*;

import legacy.error.*;
import legacy.hedge.*;

public class MainHedgingPosition {
  private final HedgingPositionManager manager;

  public MainHedgingPosition() {
    manager = new HedgingPositionManager();
  }

  public static void main(String[] args) throws Exception {
    new MainHedgingPosition().run();
  }

  private void run() {
    HedgingPosition position = createPosition(1);

    // INI
    position.setType(HedgingPositionType.INI);
    CheckResult<HedgingPosition> result = manager.initAndSendHedgingPosition(position);
    if (!result.isCheckIsOk()) {
      throw new IllegalStateException("Unable to init or send the position");
    }

    HedgingPosition resultPosition = result.getResult();
    if (resultPosition.getStatus() != HedgingPositionStatus.HEDGED) {
      throw new IllegalStateException("Unable to hedge the position");
    }

    // EXT
    position.setType(HedgingPositionType.EXT);
    result = manager.initAndSendHedgingPosition(position);
    if (!result.isCheckIsOk()) {
      throw new IllegalStateException("Unable to init or send the position");
    }

    resultPosition = result.getResult();
    if (resultPosition.getStatus() != HedgingPositionStatus.HEDGED) {
      throw new IllegalStateException("Unable to hedge the position");
    }
  }

  static HedgingPosition createPosition(int id) {
    HedgingPosition position = new HedgingPosition();
    position.setId(id);
    position.setValueDate(new Date());
    return position;
  }
}
