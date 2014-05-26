package com.retour1024;

import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

public class PersonsTest {
  public static final String BOB = "BOB";
  public static final String DAVE = "Dave";
  public static final String SLASH = "/";
  public static final int AGE_12 = 12;
  public static final Person PERSON_BOB = new Person(BOB, AGE_12);

  private Persons persons;

  @Before
  public void setUp() {
    persons = new Persons();
  }

  @After
  public void clean() {
    persons = null;
  }

  /**
   * Test vide.
   */
  @Test
  public void testEmptyList() {
    // Given
    // When
    final List<Person> list = persons.list();

    // Then
    Assert.assertNotNull(list);
    Assert.assertEquals("Doit etre vide", 0, list.size());
  }

  /**
   * Test d'ajout d'une personne.
   */
  @Test
  public void testAddPerson() {
    // Given
    final Persons persons = new Persons();

    // When
    persons.add(PERSON_BOB);
    final List<Person> list = persons.list();

    // Then
    Assert.assertNotNull(list);
    Assert.assertEquals(1, list.size());
    Assert.assertEquals("Doit etre egal a Dave", BOB, list.get(0).getName());
    Assert.assertEquals("Doit etre egal a 12", AGE_12, list.get(0).getAge());
  }

  /**
   * Test d'ajout de plusieurs personnes.
   */
  @Test
  public void testAddPersons() {
    // Given
    final Persons persons = new Persons();

    // When
    persons.add(new Person(BOB, AGE_12));
    persons.add(new Person(BOB + "_2", 26));
    final List<Person> list = persons.list();

    // Then
    Assert.assertNotNull(list);
    Assert.assertEquals(2, list.size());
    Assert.assertEquals("Premier doit etre egal a Dave", BOB, list.get(0).getName());
    Assert.assertEquals("Premier doit etre egal a 12", AGE_12, list.get(0).getAge());
    Assert.assertEquals("Deuxieme doit etre egal a Dave_2", BOB + "_2", list.get(1).getName());
    Assert.assertEquals("Deuxieme doit etre egal a 12", 26, list.get(1).getAge());
  }

  /**
   * Test de tri par age.
   */

  @Test
  public void testSortByAge() {
    // Given
    final Persons persons = new Persons();

    // When
    persons.add(new Person("Person4", 4));
    persons.add(new Person("Person2", 2));
    persons.add(new Person("Person1", 1));
    persons.add(new Person("Person5", 5));
    persons.add(new Person("Person3", 3));
    final List<Person> list = persons.listByAge();

    // Then
    assertNotNull(list);
    assertEquals(5, list.size());

    for (int i = 1; i <= 5; i++) {
      assertEquals("Person" + i, list.get(i - 1).getName());
      assertEquals(i, list.get(i - 1).getAge());
    }
  }

  /**
   * Test de tri par age.
   */
  @Test
  public void testSortByName() {
    // Given
    final Persons persons = new Persons();

    // When
    persons.add(new Person("Person4", 1));
    persons.add(new Person("Person2", 2));
    persons.add(new Person("Person1", 3));
    persons.add(new Person("Person5", 4));
    persons.add(new Person("Person3", 5));
    final List<Person> list = persons.listByName();

    // Then
    Assert.assertNotNull(list);
    Assert.assertEquals(5, list.size());
    for (int i = 1; i <= 5; i++) {
      Assert.assertEquals("Person" + i, list.get(i - 1).getName());
    }
  }

  // Error2
  @Test
  public void shouldFailWithFaultyComparator() throws NullPointerException, IllegalStateException, RuntimeException {
    // GIVEN
    persons.add(new Person(BOB + SLASH + "1", 1));
    persons.add(new Person(BOB + SLASH + "55555", 2));
    persons.add(new Person(BOB + SLASH + "333", 3));
    persons.add(new Person(BOB + SLASH + "4444", 4));
    persons.add(new Person(BOB + SLASH + "22", 5));

    // WHEN
    try {
      final List<Person> list = persons.listBy(new Comparator<Person>() {
        @Override
        public int compare(Person right, Person left) {
          throw new RuntimeException("BUG");
        }
      });
      Assert.fail("should fail");
    } catch (IllegalStateException e) {
      // OK
      assertEquals("Unable to sort", e.getMessage());
    }
  }

  // Tri
  @Test
  public void shouldSortByComparatorProvidedWhenCallingListBy() throws Exception {
    // GIVEN
    persons.add(new Person(BOB + SLASH + "1", 1));
    persons.add(new Person(BOB + SLASH + "55555", 2));
    persons.add(new Person(BOB + SLASH + "333", 3));
    persons.add(new Person(BOB + SLASH + "4444", 4));
    persons.add(new Person(BOB + SLASH + "22", 5));

    // WHEN
    final List<Person> list = persons.listBy(new Comparator<Person>() {
      @Override
      public int compare(Person right, Person left) {
        return right.getName().length() - left.getName().length();
      }
    });

    // THEN
    Assert.assertNotNull(list);
    Assert.assertEquals(5, list.size());
    Assert.assertEquals(BOB + SLASH + "1", list.get(0).getName());
    Assert.assertEquals(BOB + SLASH + "22", list.get(1).getName());
    Assert.assertEquals(BOB + SLASH + "333", list.get(2).getName());
    Assert.assertEquals(BOB + SLASH + "4444", list.get(3).getName());
    Assert.assertEquals(BOB + SLASH + "55555", list.get(4).getName());
  }

  // Error
  @Test
  public void shouldFailWithNullComparator() throws NullPointerException {
    // GIVEN
    persons.add(new Person(BOB + SLASH + "1", 1));
    persons.add(new Person(BOB + SLASH + "55555", 2));
    persons.add(new Person(BOB + SLASH + "333", 3));
    persons.add(new Person(BOB + SLASH + "4444", 4));
    persons.add(new Person(BOB + SLASH + "22", 5));

    // WHEN
    try {
      final List<Person> list = persons.listBy(null);
      Assert.fail("should fail");
    } catch (NullPointerException e) {
      // OK
    }
  }

  @Test
  public void asMap() {
    // GIVEN
    // WHEN
    persons.add(PERSON_BOB);
    persons.add(new Person(DAVE, 26));
    final Map<String, Integer> asMap = persons.asMap();
    int ageBob = asMap.get(BOB);
    int ageDave = asMap.get(DAVE);

    Assert.assertEquals(2, asMap.size());
    Assert.assertEquals("Dave a 26 ans", ageDave, 26);
    Assert.assertEquals("Bob a 12 ans", ageBob, 12);
  }
}
