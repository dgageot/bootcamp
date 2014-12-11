package legacy.json;

import java.io.*;

import legacy.hedge.*;

import com.google.common.base.*;
import com.google.common.io.*;
import com.google.gson.*;

public class HedgingPositionSerializer {
  public HedgingPosition fromJson(File input) {
    try {
      return new Gson().fromJson(Files.readFirstLine(input, Charsets.UTF_8), HedgingPosition.class);
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
  }

  public void toJson(HedgingPosition hedgingPosition, File output) {
    try {
      Files.write(new Gson().toJson(hedgingPosition), output, Charsets.UTF_8);
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
  }
}
