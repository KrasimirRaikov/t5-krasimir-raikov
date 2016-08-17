package com.clouway.bank.adapter.persistence;

import com.clouway.bank.core.AccountRepository;
import com.clouway.bank.core.DataStore;
import com.clouway.bank.core.SessionRepository;
import com.clouway.bank.core.UserRepository;
import com.google.inject.AbstractModule;

import java.sql.Connection;


/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class BankPersistentModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(Connection.class).toProvider(ConnectionProvider.class);

    bind(DataStore.class).to(DatabaseHelper.class);

    bind(AccountRepository.class).to(PersistentAccountRepository.class);

    bind(UserRepository.class).to(PersistentUserRepository.class);

    bind(SessionRepository.class).to(PersistentSessionRepository.class);
  }
}