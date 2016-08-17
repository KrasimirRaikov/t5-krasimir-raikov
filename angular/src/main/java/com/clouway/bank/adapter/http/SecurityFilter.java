package com.clouway.bank.adapter.http;

import com.clouway.bank.core.BankCalendar;
import com.clouway.bank.core.Session;
import com.clouway.bank.core.SessionProvider;
import com.clouway.bank.core.SessionRepository;
import com.clouway.bank.core.SidFinder;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
@Singleton
public class SecurityFilter implements Filter {
  private SidFinder sidFinder;
  private SessionRepository sessionRepository;
  private BankCalendar bankCalendar;
  private List<String> unsecuredPages;
  private SessionProvider sessionProvider;

  @Inject
  public SecurityFilter(SidFinder sidFinder, SessionRepository sessionRepository, BankCalendar bankCalendar, List<String> unsecuredPages, SessionProvider sessionProvider) {
    this.sidFinder = sidFinder;
    this.sessionRepository = sessionRepository;
    this.bankCalendar = bankCalendar;
    this.unsecuredPages = unsecuredPages;
    this.sessionProvider = sessionProvider;
  }

  public void destroy() {
  }

  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) resp;
    String sessionId = sidFinder.findSid(request);
    final String uri = request.getRequestURI();
    boolean uriAccessible = unsecuredPages.contains(uri);

    Optional<Session> session = Optional.ofNullable(sessionRepository.retrieve(sessionId));
    boolean sessionActive = session.isPresent() && session.get().expirationTime > bankCalendar.getCurrentTime();

    if (uriAccessible && sessionActive) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().println("already logged in");
      return;
    }
    if (!uriAccessible && sessionActive) {
      Session updatedSession = new Session(session.get().id, session.get().userId, bankCalendar.getExpirationTime());
      sessionRepository.update(updatedSession);
      sessionProvider.set(updatedSession);
      chain.doFilter(request, response);
      return;
    }
    if (uriAccessible || sessionActive) {
      chain.doFilter(request, response);
      return;
    }
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.getWriter().println("you need to login");
  }


  public void init(FilterConfig config) throws ServletException {
  }

}

