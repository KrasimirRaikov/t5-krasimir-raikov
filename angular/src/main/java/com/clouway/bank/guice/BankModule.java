package com.clouway.bank.guice;

import com.clouway.bank.core.BankTimeCalendar;
import com.clouway.bank.adapter.http.validation.BankTransactionValidator;
import com.clouway.bank.adapter.http.validation.UserDataValidator;
import com.clouway.bank.adapter.persistence.BankPersistentModule;
import com.clouway.bank.core.BankCalendar;
import com.clouway.bank.core.CurrentSessionProvider;
import com.clouway.bank.core.SessionProvider;
import com.clouway.bank.core.TransactionValidator;
import com.clouway.bank.core.UserValidator;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import java.util.Calendar;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class BankModule extends AbstractModule {
  @Override
  protected void configure() {
    install(new BankPersistentModule());

    bind(SessionProvider.class).to(CurrentSessionProvider.class);

    bind(TransactionValidator.class).to(BankTransactionValidator.class);

    bind(UserValidator.class).to(UserDataValidator.class);
  }

  @Provides
  BankCalendar getBankTimeCalendar() {
    return new BankTimeCalendar(Calendar.getInstance(), 5);
  }
}
