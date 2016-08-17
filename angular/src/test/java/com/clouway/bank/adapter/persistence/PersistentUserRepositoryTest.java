package com.clouway.bank.adapter.persistence;

import com.clouway.bank.core.DataStore;
import com.clouway.bank.core.RowFetcher;
import com.clouway.bank.core.User;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class PersistentUserRepositoryTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  private DataStore dataStore;

  private PersistentUserRepository userRepository;

  @Before
  public void setUp() {
    userRepository = new PersistentUserRepository(dataStore);

  }

  @Test
  public void registerUser() {
    User john = new User("John01", "123456");

    List<User> johnFromStore = new ArrayList<>();
    johnFromStore.add(john);

    context.checking(new Expectations() {{
      oneOf(dataStore).fetchRows(with(equalTo("SELECT * FROM users WHERE username = ?;")), with(any(RowFetcher.class)), with(equalTo(new Object[]{"John01"})));
      will(returnValue(new ArrayList<User>()));

      oneOf(dataStore).executeQuery("INSERT INTO users(username, password) VALUES(?, ?);", new Object[]{"John01", "123456"});

      oneOf(dataStore).fetchRows(with(equalTo("SELECT * FROM users WHERE username = ?;")), with(any(RowFetcher.class)), with(equalTo(new Object[]{"John01"})));
      will(returnValue(johnFromStore));
    }});
    userRepository.register(john);

    User returnedUser = userRepository.getUserById("John01");

    assertThat(returnedUser, is(equalTo(john)));
  }

}
