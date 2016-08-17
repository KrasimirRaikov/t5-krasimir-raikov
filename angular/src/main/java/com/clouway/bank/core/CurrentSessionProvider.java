package com.clouway.bank.core;

import com.google.inject.Singleton;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
@Singleton
public class CurrentSessionProvider implements SessionProvider {
  private Session session;

  @Override
  public Session get() {
    return session;
  }

  @Override
  public void set(Session session) {
    this.session = session;
  }

}
