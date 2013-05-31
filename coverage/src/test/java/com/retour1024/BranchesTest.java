package com.retour1024;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class BranchesTest {
  @Test
  public void should_be_ok() {
    String ok = new Branches().isOk(true);

    assertThat(ok).isEqualTo("OK");
  }
}
