package com.clouway.bank.adapter.http;

import com.clouway.bank.core.AccountPager;
import com.clouway.bank.core.AccountRecord;
import com.clouway.bank.core.SessionProvider;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
@Singleton
public class AccountHistoryService extends HttpServlet {
  private Gson gson;
  private AccountPager accountPager;
  private SessionProvider sessionProvider;

  @Inject
  public AccountHistoryService(Gson gson, AccountPager accountPager, SessionProvider sessionProvider) {
    this.gson = gson;
    this.accountPager = accountPager;
    this.sessionProvider = sessionProvider;
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String userId = sessionProvider.get().userId;
    String page = request.getParameter("page");
    int requestPage;

    try {
      requestPage = Integer.parseInt(page) - 1;
      if (requestPage < 0) {
        requestPage = 0;
      }
    } catch (Exception e) {
      response.setStatus(400);
      response.getWriter().println("no such page");
      return;
    }

    List<AccountRecord> accountRecords = accountPager.requestPage(userId, requestPage);
    if (accountRecords != null) {
      response.getWriter().println(gson.toJson(accountRecords));
    } else {
      response.setStatus(400);
      response.getWriter().println("page not found");
      return;
    }
  }

}
