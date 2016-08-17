package com.clouway.bank.core;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public interface UserRepository {

  /**
   * finds the user by name
   *
   * @param userId the user name
   * @return the User
   */
  User getUserById(String userId);
}
