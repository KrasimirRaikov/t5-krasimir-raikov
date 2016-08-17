package com.clouway.bank.guice;


import com.clouway.bank.http.ConnectionFilter;
import com.clouway.bank.http.DepositController;
import com.google.inject.servlet.ServletModule;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */

public class BankServletModule extends ServletModule {

    @Override
    protected void configureServlets() {
        install(new BankModule());

        filter("/*").through(ConnectionFilter.class);

        serve("/depositcontroller").with(DepositController.class);
    }

}


