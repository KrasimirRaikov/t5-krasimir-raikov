package com.clouway.bank.adapter.http;

import com.clouway.bank.core.AccountRepository;
import com.clouway.bank.core.SessionProvider;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
@Singleton
public class AccountBalanceService extends HttpServlet {

  private final AccountRepository accountRepository;
  private final SessionProvider sessionProvider;

  @Inject
  public AccountBalanceService(AccountRepository accountRepository, SessionProvider sessionProvider) {
    this.accountRepository = accountRepository;
    this.sessionProvider = sessionProvider;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Double currentBalance = accountRepository.getCurrentBalance(sessionProvider.get().userId);
    resp.getWriter().println(currentBalance);
  }
}
