package com.clouway.bank.guice;

import com.clouway.bank.core.AccountRepository;
import com.clouway.bank.core.ConnectionProvider;
import com.clouway.bank.core.DataStore;
import com.clouway.bank.persistence.DatabaseHelper;
import com.clouway.bank.persistence.PerRequestConnectionProvider;
import com.clouway.bank.persistence.PersistentAccountRepository;
import com.google.inject.AbstractModule;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class BankPersistentModule extends AbstractModule {
    @Override
    protected void configure() {

        bind(ConnectionProvider.class).to(PerRequestConnectionProvider.class);

        bind(DataStore.class).to(DatabaseHelper.class);

        bind(AccountRepository.class).to(PersistentAccountRepository.class);

    }
}
