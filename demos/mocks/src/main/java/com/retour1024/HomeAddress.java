package com.retour1024;

public class HomeAddress implements Address {
  private final String city;
  private final String country;

  public HomeAddress(String city, String country) {
    this.city = city;
    this.country = country;
  }

  @Override
  public String getCity() {
    return city;
  }

  @Override
  public String getCountry() {
    return country;
  }
}
