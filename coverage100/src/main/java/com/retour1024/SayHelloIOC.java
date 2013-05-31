package com.retour1024;

public class SayHelloIOC {
  private final GreetingFactory greetingFactory;

  public SayHelloIOC(GreetingFactory greetingFactory) {
    this.greetingFactory = greetingFactory;
  }

  public String sayHelloTo(String whom) {
    String greeting = greetingFactory.create();

    return greeting + " " + whom;
  }

  public static class GreetingFactory {
    public String create() {
      return "Hello";
    }
  }
}
