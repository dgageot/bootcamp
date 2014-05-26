package com.retour1024;

import com.google.common.collect.*;

import java.util.*;

import static com.google.common.base.Preconditions.*;

public class Persons {
  private static final Comparator<Person> BY_AGE = Ordering.natural().onResultOf(Person.TO_AGE);
  private static final Comparator<Person> BY_NAME = Ordering.natural().onResultOf(Person.TO_NAME);

  private final List<Person> list = new ArrayList<Person>();

  public void add(Person person) {
    list.add(person);
  }

  public List<Person> list() {
    return Collections.unmodifiableList(list);
  }

  public List<Person> listByAge() {
    return listBy(BY_AGE);
  }

  public List<Person> listByName() {
    return listBy(BY_NAME);
  }

  public List<Person> listBy(Comparator<Person> comparator) {
    checkNotNull(comparator);

    try {
      return Ordering.from(comparator).immutableSortedCopy(list);
    } catch (Exception e) {
      throw new IllegalStateException("Unable to sort", e);
    }
  }

  public Map<String, Integer> asMap() {
    Map<String, Integer> map = Maps.newHashMap();
    for (Person person : list) {
      map.put(person.getName(), person.getAge());
    }
    return map;
  }
}