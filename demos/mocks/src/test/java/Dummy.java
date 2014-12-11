import com.retour1024.Address;
import com.retour1024.HomeAddress;
import com.retour1024.Person;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class Dummy {


  @Test
  public void with_null() {
    Person person = new Person("Bob", null);

    assertThat(person.getName()).isEqualTo("Bob");
  }

  @Test
  public void with_impl() {
    Person person = new Person("Bob", new HomeAddress(null, null));

    assertThat(person.getName()).isEqualTo("Bob");
  }

  @Test
  public void with_test_impl() {
    Person person = new Person("Bob", new DummyAddress());

    assertThat(person.getName()).isEqualTo("Bob");
  }

  static class DummyAddress implements Address {
    @Override
    public String getCity() {
      return null;
    }

    @Override
    public String getCountry() {
      return null;
    }
  }

  @Test
  public void with_mock() {
    Person person = new Person("Bob", Mockito.mock(Address.class));

    assertThat(person.getName()).isEqualTo("Bob");
  }

  @Test
  public void with_mock_short() {
    Person person = new Person("Bob", mock(Address.class));

    assertThat(person.getName()).isEqualTo("Bob");
  }



}
