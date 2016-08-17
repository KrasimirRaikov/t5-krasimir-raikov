package com.clouway.bank.adapter.persistence;

import com.clouway.bank.core.AccountRepository;
import com.clouway.bank.core.Amount;
import com.clouway.bank.core.DataStore;
import com.clouway.bank.core.RowFetcher;
import com.clouway.bank.core.ValidationException;
import com.google.inject.Inject;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class PersistentAccountRepository implements AccountRepository {
  private DataStore dataStore;

  @Inject
  public PersistentAccountRepository(DataStore dataStore) {
    this.dataStore = dataStore;
  }


  /**
   * Deposits funds
   *
   * @param amount funds to deposit
   */
  @Override
  public Double deposit(Amount amount) {
    String updateQuery = "UPDATE account SET balance=balance+? WHERE username=?";
    dataStore.executeQuery(updateQuery, amount.value, amount.userId);

    return getCurrentBalance(amount.userId);
  }

  /**
   * @param amount of funds to withdraw
   * @return
   * @throws ValidationException
   */
  @Override
  public Double withdraw(Amount amount) throws ValidationException {
    String updateQuery = "UPDATE account SET balance=balance-? WHERE username=?";
    if (getCurrentBalance(amount.userId) < amount.value) {
      throw new ValidationException("insufficient amount");
    }

    dataStore.executeQuery(updateQuery, amount.value, amount.userId);

    return getCurrentBalance(amount.userId);
  }

  /**
   * Gets the current value of funds in the balance
   *
   * @param userId user identification
   * @return the current balance
   */
  @Override
  public Double getCurrentBalance(String userId) {
    String selectQuery = "SELECT balance FROM account WHERE username=?";
    RowFetcher<Double> rowFetcher = rs -> rs.getDouble("balance");

    return dataStore.fetchRows(selectQuery, rowFetcher, userId).get(0);
  }
}
