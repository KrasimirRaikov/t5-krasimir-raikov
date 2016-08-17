package com.clouway.bank.core;

import java.util.List;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public interface AccountPager {

  /**
   * makes a request for a page, if this page exists it is being returned,
   * else the one closest to it is.
   *
   * @param userId     the id of the user
   * @param pageNumber the number of the page
   * @return the account records on that page
   */
  List<AccountRecord> requestPage(String userId, int pageNumber);

}
