package com.clouway.bank.adapter.persistence;

import com.clouway.bank.core.AccountHistoryRepository;
import com.clouway.bank.core.AccountRecord;
import com.clouway.bank.core.DataStore;
import com.clouway.bank.core.RowFetcher;
import com.google.inject.Inject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class PersistentAccountHistoryRepository implements AccountHistoryRepository {
  private DataStore dataStore;

  @Inject
  public PersistentAccountHistoryRepository(DataStore dataStore) {
    this.dataStore = dataStore;
  }

  @Override
  public List<AccountRecord> getAccountRecords(String userId, int offset, int limit) {
    String query = "SELECT date, username, operation, amount FROM account_history WHERE username=? ORDER BY date LIMIT ? OFFSET ?;";

    RowFetcher<AccountRecord> rowFetcher = rs -> new AccountRecord(rs.getTimestamp("date").getTime(), rs.getString("username"), rs.getString("operation"), roundUp(rs.getDouble("amount"), 2));
    return dataStore.fetchRows(query, rowFetcher, userId, limit, offset);
  }

  private double roundUp(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }
}
