package com.retour1024;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CalcTest {
  Calc calc = new Calc();

  @Test
  public void should_return_constant() {
    assertThat(calc.execute("0")).isZero();
    assertThat(calc.execute("42")).isEqualTo(42);
  }

  @Test
  public void should_sum_two_values() {
    assertThat(calc.execute("1+2")).isEqualTo(3);
    assertThat(calc.execute("5+6")).isEqualTo(11);
  }
}
