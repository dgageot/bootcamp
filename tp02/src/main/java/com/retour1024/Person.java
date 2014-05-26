package com.retour1024;

import com.google.common.base.*;

public class Person {
  private final String name;
  private final int age;

  public Person(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public static final Function<Person, Integer> TO_AGE = new Function<Person, Integer>() {
    @Override
    public Integer apply(Person person) {
      return person.getAge();
    }
  };

  public static final Function<Person, String> TO_NAME = new Function<Person, String>() {
    @Override
    public String apply(Person person) {
      return person.getName();
    }
  };
}
