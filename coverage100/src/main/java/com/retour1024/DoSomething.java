package com.retour1024;

public class DoSomething {
  Action action;

  public DoSomething(Action action) {
    this.action = action;
  }

  public boolean doSomething() {
    if (action.step1() && action.step2() && action.step3()) {
      // Do something
      return true;
    }
    return false;
  }

  public static class Action {
    public boolean step1() {
      try {
        return true;
      } catch (Exception e) {
        return false;
      }
    }

    public boolean step2() {
      try {
        return true;
      } catch (Exception e) {
        return false;
      }
    }

    public boolean step3() {
      try {
        return true;
      } catch (Exception e) {
        return false;
      }
    }
  }
}
