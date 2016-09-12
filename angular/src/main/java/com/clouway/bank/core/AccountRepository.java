package com.clouway.bank.core;

/**
 * Will store and get the account data
 *
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public interface AccountRepository {

  /**
   * deposits funds
   *
   * @param amount funds to deposit
   */
  Double deposit(Amount amount);

  /**
   * withdraw funds
   *
   * @param amount funds to withdraw
   */
  Double withdraw(Amount amount);

  /**
   * will return the current state of the balance
   *
   * @param userId user identification
   * @return the balance for the given user
   */
  Double getCurrentBalance(String userId);
}