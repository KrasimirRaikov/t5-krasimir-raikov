package com.clouway.bank.persistence;

import com.clouway.bank.core.*;
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

    @Override
    public Double withdraw(Amount amount){
        return null;
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

    /**
     * initiates empty account for the user
     *
     * @param userId the users unique name
     */
    @Override
    public void createAccount(String userId) {
        String insertQuery = "INSERT INTO account(username, balance) VALUES(?, ?);";
        dataStore.executeQuery(insertQuery, userId, 0D);
    }
}
