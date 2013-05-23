package com.retour1024;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class StatsTest {
  @Test
  public void should_sum_ages() {
    List<Person> persons = Lists.newArrayList();
    persons.add(new Person(null, 10, 0, null));
    persons.add(new Person(null, 20, 0, null));
    persons.add(new Person(null, 30, 0, null));

    Stats stats = new Stats(persons);
    int sumAges = stats.sumAges();

    assertThat(sumAges)
        .isEqualTo(10 + 20 + 30)
        .isEqualTo(60);
  }

  @Test
  public void should_collect_names() {
    List<Person> persons = Lists.newArrayList();
    persons.add(new Person("A", 0, 0, null));
    persons.add(new Person("B", 0, 0, null));
    persons.add(new Person("C", 0, 0, null));

    final List<String> names = Lists.newArrayList();

    Stats stats = new Stats(persons);
    stats.forEach(new PersonAction() {
      @Override
      public void doSomething(Person person) {
        names.add(person.getName());
      }
    });

    assertThat(names).containsExactly("A", "B", "C");
  }
}
