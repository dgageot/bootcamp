package com.retour1024;

public class Calc {
  public int execute(String expression) {
    if (expression.contains("+")) {
      String[] parts = expression.split("[+]");

      int left = toInt(parts[0]);
      int right = toInt(parts[1]);

      return left + right;
    }

    return toInt(expression);
  }

  private static int toInt(String part) {
    return Integer.parseInt(part);
  }
}
