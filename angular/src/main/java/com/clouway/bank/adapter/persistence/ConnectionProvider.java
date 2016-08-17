package com.clouway.bank.adapter.persistence;

import com.clouway.bank.adapter.http.ConnectionFilter;
import com.google.inject.Provider;
import com.google.inject.servlet.SessionScoped;

import java.sql.Connection;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */

public class ConnectionProvider implements Provider<Connection> {

  @Override
  @SessionScoped
  public Connection get() {
    return ConnectionFilter.getConnection();
  }
}
