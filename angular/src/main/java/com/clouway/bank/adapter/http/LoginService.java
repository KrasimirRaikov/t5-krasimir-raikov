package com.clouway.bank.adapter.http;

import com.clouway.bank.core.BankCalendar;
import com.clouway.bank.core.Session;
import com.clouway.bank.core.SessionRepository;
import com.clouway.bank.core.User;
import com.clouway.bank.core.UserRepository;
import com.clouway.bank.core.UserValidator;
import com.google.gson.Gson;
import com.google.inject.Inject;

import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
@Singleton
public class LoginService extends HttpServlet {
  private final Gson gson;
  private final UserRepository userRepository;
  private final UserValidator userValidator;
  private final SessionRepository sessionRepository;
  private final BankCalendar calendar;

  @Inject
  public LoginService(Gson gson, UserRepository userRepository, UserValidator userValidator, SessionRepository sessionRepository, BankCalendar calendar) {
    this.gson = gson;
    this.userRepository = userRepository;
    this.userValidator = userValidator;
    this.sessionRepository = sessionRepository;
    this.calendar = calendar;
  }


  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    User user = gson.fromJson(req.getReader(), User.class);

    String userValidationMessage = userValidator.validate(user);

    if (!"".equals(userValidationMessage)) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      resp.getWriter().println(userValidationMessage);
      return;
    }


      User repositoryUser = userRepository.getUserById(user.username);
      if(repositoryUser==null){
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.getWriter().println("Username incorrect/missing");
        return;
      }
      String message = userValidator.passwordsMatch(repositoryUser.password, user.password);

      if (!"".equals(message)) {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.getWriter().println(message);
        return;
      }

      String sessionId = UUID.randomUUID().toString();
      sessionRepository.create(new Session(sessionId, user.username, calendar.getExpirationTime()));

      resp.setStatus(HttpServletResponse.SC_OK);
      resp.getWriter().print(sessionId);
  }
}
