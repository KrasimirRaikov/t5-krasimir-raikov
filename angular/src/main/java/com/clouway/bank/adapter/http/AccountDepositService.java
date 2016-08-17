package com.clouway.bank.adapter.http;

import com.clouway.bank.core.AccountRepository;
import com.clouway.bank.core.Amount;
import com.clouway.bank.core.SessionProvider;
import com.clouway.bank.core.TransactionReport;
import com.clouway.bank.core.TransactionValidator;
import com.google.gson.Gson;
import com.google.inject.Inject;

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
public class AccountDepositService extends HttpServlet {
  private final AccountRepository accountRepository;
  private final TransactionValidator validator;
  private final SessionProvider sessionProvider;
  private Gson gson;

  @Inject
  public AccountDepositService(AccountRepository accountRepository, TransactionValidator validator, SessionProvider sessionProvider, Gson gson) {
    this.accountRepository = accountRepository;
    this.validator = validator;
    this.sessionProvider = sessionProvider;
    this.gson = gson;
  }


  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String amount = request.getParameter("amount");

    String message = "";

    message = validator.validateAmount(amount);

    if (message.isEmpty()) {
      Double dbAmount = Double.parseDouble(amount);
      Double currentBalance = accountRepository.deposit(new Amount(sessionProvider.get().userId, dbAmount));
      message = amount + " were deposited into your account";

      response.setContentType("application/json");
      response.getWriter().println(gson.toJson(new TransactionReport(currentBalance, message)));
    } else {
      response.setStatus(400);
      response.getWriter().println(message);
    }

  }

}
