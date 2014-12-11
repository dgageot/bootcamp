package com.retour1024;

public class EmailFactory {
  public String createBody(String email, String message) {
    return "Hello " + email + ", " + message;
  }
}
