package com.retour1024;

public class Person {
  private final String name;
  private final int age;
  private final double size;
  private final String city;

  public Person(String name, int age, double size, String city) {
    this.name = name;
    this.age = age;
    this.size = size;
    this.city = city;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public double getSize() {
    return size;
  }

  public String getCity() {
    return city;
  }
}
