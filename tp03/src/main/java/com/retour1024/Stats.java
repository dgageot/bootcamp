package com.retour1024;

public class Stats {
  private final Iterable<Person> persons;

  public Stats(Iterable<Person> persons) {
    this.persons = persons;
  }

  public int sumAges() {
    int sum = 0;
    for (Person person : persons) {
      sum += person.getAge();
    }
    return sum;
  }

  public void forEach(PersonAction personAction) {
    for (Person person : persons) {
      personAction.doSomething(person);
    }
  }
}
