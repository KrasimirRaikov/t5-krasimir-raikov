package com.clouway.bank.adapter.http;

import com.clouway.bank.core.Session;
import com.clouway.bank.core.SessionProvider;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
@Singleton
public class AccountIdService extends HttpServlet {

  private final SessionProvider sessionProvider;

  @Inject
  public AccountIdService(SessionProvider sessionProvider) {

    this.sessionProvider = sessionProvider;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Session currentSession = sessionProvider.get();
    resp.getWriter().println(currentSession.userId);
  }
}
