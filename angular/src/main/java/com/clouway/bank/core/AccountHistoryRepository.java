package com.clouway.bank.core;

import java.util.List;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public interface AccountHistoryRepository {

  /**
   * Loads the users account records.
   *
   * @param userId the id of the user
   * @param offset offset of the records
   * @param limit  limit of the records
   * @return
   */
  List<AccountRecord> getAccountRecords(String userId, int offset, int limit);
}
