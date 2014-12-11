package com.retour1024;

import java.util.Scanner;

public class AskUsername {
  private final NameVerifier nameVerifier = new NameVerifier();

  public String ask() {
    System.out.println("What is your name?");

    Scanner scanner = new Scanner(System.in);
    try {
      String name = scanner.nextLine();

      nameVerifier.verifyName(name);

      return name;
    } finally {
      scanner.close();
    }
  }
}
