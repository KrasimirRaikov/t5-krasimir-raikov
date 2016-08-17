package com.clouway.bank.adapter.persistence;

import com.clouway.bank.core.DataStore;
import com.clouway.bank.core.RowFetcher;
import com.clouway.bank.core.User;
import com.clouway.bank.core.UserRepository;
import com.clouway.bank.core.ValidationException;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class PersistentUserRepository implements UserRepository {
  private DataStore dataStore;

  @Inject
  public PersistentUserRepository(DataStore dataStore) {
    this.dataStore = dataStore;
  }

  /**
   * Registers the user
   *
   * @param user the user object
   */
  @Override
  public void register(User user) {
    String query = "INSERT INTO users(username, password) VALUES(?, ?);";

    if (getUserById(user.username) != null) {
      throw new ValidationException("username is taken");
    }
    dataStore.executeQuery(query, user.username, user.password);
  }


  /**
   * Finds user by it's name
   *
   * @param userId the user name
   * @return the user
   */
  public User getUserById(String userId) {
    String query = "SELECT * FROM users WHERE username = ?;";

    RowFetcher<User> rowFetcher = rs -> new User(rs.getString(1), rs.getString(2));
    List<User> userList = new ArrayList<>();
    userList = dataStore.fetchRows(query, rowFetcher, userId);
    if (userList.isEmpty()) {
      return null;
    }

    return userList.get(0);
  }
}
