package com.retour1024;

import com.google.common.annotations.VisibleForTesting;

public class SayHelloProtected {
  public String sayHelloTo(String whom) {
    String greeting = createGreeting();

    return greeting + " " + whom;
  }

  @VisibleForTesting
  protected String createGreeting() {
    return "Hello";
  }
}
