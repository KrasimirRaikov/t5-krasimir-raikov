package com.clouway.bank.adapter.persistence;

import com.clouway.bank.core.RowFetcher;
import com.clouway.bank.utils.DatabaseConnectionRule;
import com.clouway.bank.utils.UserRepositoryUtility;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class DatabaseHelperTest {

  @Rule
  public DatabaseConnectionRule connectionProvider = new DatabaseConnectionRule("bank_test");
  private DatabaseHelper databaseHelper;
  private UserRepositoryUtility userRepositoryUtility;

  @Before
  public void setUp() {
    databaseHelper = new DatabaseHelper(connectionProvider.getConnection());
    userRepositoryUtility = new UserRepositoryUtility(connectionProvider.getConnection());
    userRepositoryUtility.clearUsersTable();
  }

  @Test
  public void insertDataIntoTable() {
    User petar = new User("petar1", "123456");
    String insertUser = "INSERT INTO users(username, password) VALUES(?, ?);";
    String getUser = "SELECT * FROM users WHERE username = ?;";
    RowFetcher<User> rowFetcher = new RowFetcher<User>() {
      @Override
      public User fetchRow(ResultSet rs) throws SQLException {
        return new User(rs.getString("username"), rs.getString("password"));
      }
    };
    databaseHelper.executeQuery(insertUser, petar.id, petar.password);

    List<User> petarList = databaseHelper.fetchRows(getUser, rowFetcher, petar.id);

    assertThat(petarList.get(0), is(equalTo(petar)));
  }

  class User {
    public final String id;
    public final String password;

    User(String id, String password) {
      this.id = id;
      this.password = password;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      User user = (User) o;

      if (id != null ? !id.equals(user.id) : user.id != null) return false;
      return password != null ? password.equals(user.password) : user.password == null;

    }

    @Override
    public int hashCode() {
      int result = id != null ? id.hashCode() : 0;
      result = 31 * result + (password != null ? password.hashCode() : 0);
      return result;
    }
  }

}
