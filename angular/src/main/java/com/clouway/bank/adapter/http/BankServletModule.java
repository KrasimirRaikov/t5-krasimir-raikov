package com.clouway.bank.adapter.http;


import com.clouway.bank.core.BankCalendar;
import com.clouway.bank.core.SessionProvider;
import com.clouway.bank.core.SessionRepository;
import com.clouway.bank.core.SidFinder;
import com.clouway.bank.guice.BankModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */

public class BankServletModule extends ServletModule {

  @Override
  protected void configureServlets() {
    install(new BankModule());

    filter("/*").through(ConnectionFilter.class);
    filter("/*").through(SecurityFilter.class);

    serve("/r/account/login").with(LoginService.class);

    serve("/r/account/deposit").with(AccountDepositService.class);

    serve("/r/account/withdraw").with(AccountWithdrawService.class);

    serve("/r/account/balance").with(AccountBalanceService.class);

    serve("/r/account/name").with(AccountIdService.class);

    serve("/r/account/logout").with(LogoutService.class);
  }

  @Provides
  @Singleton
  SecurityFilter getSecurityFilter(SidFinder sidFinder,SessionRepository sessionRepository, BankCalendar bankCalendar, SessionProvider sessionProvider) {
    List<String> unsecuredPages = new ArrayList<>();
    unsecuredPages.add("/r/account/login");
    unsecuredPages.add("/r/account/register");
    return new SecurityFilter(sidFinder, sessionRepository, bankCalendar, unsecuredPages, sessionProvider);
  }
}


