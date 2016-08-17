package com.clouway.bank.guice;

import com.clouway.bank.adapter.http.validation.BankTransactionValidator;
import com.clouway.bank.adapter.persistence.BankPersistentModule;
import com.clouway.bank.core.AccountRepository;
import com.clouway.bank.core.CurrentSessionProvider;
import com.clouway.bank.core.SessionProvider;
import com.clouway.bank.core.TransactionValidator;
import com.google.inject.AbstractModule;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class BankModule extends AbstractModule {
  @Override
  protected void configure() {
    install(new BankPersistentModule());

    bind(SessionProvider.class).to(CurrentSessionProvider.class);

    bind(TransactionValidator.class).to(BankTransactionValidator.class);

  }
}
