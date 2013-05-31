package com.retour1024;

import com.google.common.primitives.Booleans;

import static com.google.common.base.Predicates.equalTo;
import static com.google.common.collect.Iterables.any;
import static com.google.common.primitives.Booleans.asList;

public class IsOk {
  public String isOk(boolean flag1, boolean flag2, boolean flag3) {
    if (flag1 || flag2 || flag3) {
      return "OK";
    }
    return "KO";
  }

  public String isOkFunctional(boolean... flags) {
    if (Booleans.contains(flags, true)) {
      return "OK";
    }
    return "KO";
  }

  public String isOkFunctional2(boolean... flags) {
    if (any(asList(flags), equalTo(true))) {
      return "OK";
    }
    return "KO";
  }
}
