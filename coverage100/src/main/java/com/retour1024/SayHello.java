package com.retour1024;

public class SayHello {
  public String sayHelloTo(String whom) {
    String greeting = new GreetingFactory().create();

    return greeting + " " + whom;
  }

  public static class GreetingFactory {
    public String create() {
      return "Hello";
    }
  }
}
