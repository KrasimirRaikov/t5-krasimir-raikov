package com.clouway.bank.server;

import com.clouway.bank.core.BankTimeCalendar;
import com.clouway.bank.adapter.http.Jetty;
import com.clouway.bank.adapter.persistence.ConnectionManager;
import com.clouway.bank.adapter.persistence.SessionCleaner;

import java.util.Calendar;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class JettyRunner {

  /**
   * Starts the server
   * listening to port 8080
   * using 'bank' database
   *
   * @param args
   */
  public static void main(String[] args) {
    ConnectionManager connectionManager = new ConnectionManager("bank", "root", "clouway.com");
    SessionCleaner sessionCleaner = new SessionCleaner(new BankTimeCalendar(Calendar.getInstance(), 5), 10, connectionManager);
    Jetty jetty = new Jetty(8080);
    sessionCleaner.clearDeadSessions();
    jetty.start();

  }
}
