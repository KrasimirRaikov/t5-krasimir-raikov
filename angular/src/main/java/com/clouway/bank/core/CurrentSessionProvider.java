package com.clouway.bank.core;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class CurrentSessionProvider implements SessionProvider {
  private Session session = new Session("1", "Ivan", 1L);

  @Override
  public Session get() {
    return session;
  }
}
