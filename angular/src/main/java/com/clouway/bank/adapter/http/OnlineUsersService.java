package com.clouway.bank.adapter.http;

import com.clouway.bank.core.BankCalendar;
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
public class OnlineUsersService extends HttpServlet {
  private SessionRepository sessionRepository;
  private BankCalendar calendar;

  @Inject
  public OnlineUsersService(SessionRepository sessionRepository, BankCalendar calendar) {

    this.sessionRepository = sessionRepository;
    this.calendar = calendar;
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Integer onlineUsers = sessionRepository.countActive(calendar.getCurrentTime());
    if (onlineUsers == null) {
      response.setStatus(500);
      response.getWriter().println("sorry, we were unable to count the active users");
      return;
    }
    response.getWriter().println(onlineUsers);
  }

}
