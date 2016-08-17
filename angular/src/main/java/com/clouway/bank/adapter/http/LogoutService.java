package com.clouway.bank.adapter.http;

import com.clouway.bank.core.Session;
import com.clouway.bank.core.SessionProvider;
import com.clouway.bank.core.SessionRepository;
import com.google.inject.Inject;

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
public class LogoutService extends HttpServlet {
  private SessionRepository sessionRepository;
  private SessionProvider sessionProvider;

  @Inject
  public LogoutService(SessionRepository sessionRepository, SessionProvider sessionProvider) {
    this.sessionRepository = sessionRepository;
    this.sessionProvider = sessionProvider;
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Session session = sessionProvider.get();
    sessionRepository.remove(session.id);
    response.setStatus(HttpServletResponse.SC_OK);
    response.getWriter().println(session.id);
  }
}
