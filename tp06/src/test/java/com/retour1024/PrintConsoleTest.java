package com.retour1024;

import org.junit.Test;

public class PrintConsoleTest {
  @Test
  public void should_print_hello_to_console() {
    PrintConsole printConsole = new PrintConsole();
    printConsole.sayHelloTo("Dave");

    // Assert
  }
}
