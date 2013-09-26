package com.retour1024;

public class Main {
  public static void main(String[] args) {
    String username = new AskUsername().ask();

    System.out.printf("Hello %s!%n", username);
  }
}
