package com.retour1024;

import net.codestory.http.WebServer;

public class MainWeb {
  public static void main(String[] args) {
    new WebServer().configure(new WebConfiguration()).start();
  }
}
