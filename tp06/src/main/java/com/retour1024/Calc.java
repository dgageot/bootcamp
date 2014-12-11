package com.retour1024;

public class Calc {
  public int execute(String expression) {
    if (expression.contains("+")) {
      String[] parts = expression.split("[+]");

      int left = Integer.parseInt(parts[0]);
      int right = Integer.parseInt(parts[1]);

      return left + right;
    }

    return Integer.parseInt(expression);
  }
}
