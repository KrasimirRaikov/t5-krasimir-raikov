package com.clouway.bank.adapter.http;


import com.clouway.bank.guice.BankModule;
import com.google.inject.servlet.ServletModule;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */

public class BankServletModule extends ServletModule {

  @Override
  protected void configureServlets() {
    install(new BankModule());

    filter("/*").through(ConnectionFilter.class);

    serve("/r/account/deposit").with(AccountDepositService.class);

    serve("/r/account/balance").with(AccountBalanceService.class);
  }
}


