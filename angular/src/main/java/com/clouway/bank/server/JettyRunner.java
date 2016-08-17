package com.clouway.bank.server;

import com.clouway.bank.adapter.http.Jetty;

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
    Jetty jetty = new Jetty(8080);
    jetty.start();
  }
}
