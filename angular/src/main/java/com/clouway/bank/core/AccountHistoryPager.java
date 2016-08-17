package com.clouway.bank.core;

import com.google.inject.Inject;

import java.util.List;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class AccountHistoryPager implements AccountPager {
  private int pageSize;
  private AccountHistoryRepository historyRepository;
  private int currentPage = 0;
  private boolean hasNext = true;


  @Inject
  public AccountHistoryPager(int pageSize, AccountHistoryRepository historyRepository) {
    this.pageSize = pageSize;
    this.historyRepository = historyRepository;
  }

  @Override
  public List<AccountRecord> requestPage(String userId, int pageNumber) {
    List<AccountRecord> records;

    if (pageNumber > currentPage && hasNext) {
      records = historyRepository.getAccountRecords(userId, pageNumber * pageSize, pageSize + 1);

      hasNext = records.size() >= pageSize;
      this.currentPage = pageNumber;
      return records;
    }
    if (pageNumber > currentPage && !hasNext) {
      return null;
    }
    records = historyRepository.getAccountRecords(userId, pageNumber * pageSize, pageSize + 1);
    this.currentPage = pageNumber;
    hasNext = true;
    return records;
  }

}
