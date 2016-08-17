package com.clouway.bank.adapter.http;

import com.clouway.bank.core.AccountRepository;
import com.clouway.bank.core.RegistrationDto;
import com.clouway.bank.core.User;
import com.clouway.bank.core.UserRepository;
import com.clouway.bank.core.UserValidator;
import com.clouway.bank.core.ValidationException;
import com.google.gson.Gson;
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
public class RegistrationService extends HttpServlet {
  private Gson gson;
  private UserRepository userRepository;
  private AccountRepository accountRepository;
  private UserValidator validator;

  @Inject
  public RegistrationService(Gson gson, UserRepository userRepository, AccountRepository accountRepository, UserValidator validator) {
    this.gson = gson;
    this.userRepository = userRepository;
    this.accountRepository = accountRepository;
    this.validator = validator;
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    RegistrationDto registrationDto = gson.fromJson(request.getReader(), RegistrationDto.class);
    User user = new User(registrationDto.username, registrationDto.password);

    String message;
    try {
      String validationMessage = String.format("%1$s%2$s", validator.validate(user), validator.passwordsMatch(user.password, registrationDto.confirmPassword));

      if (!"".equals(validationMessage)) {
        message = validationMessage;
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println(message);
        return;
      }
      userRepository.register(user);

      message = "successful registration";

      accountRepository.createAccount(user.username);
      response.getWriter().println(message);
    } catch (ValidationException ex) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().println(ex.getMessage());
      return;
    }


  }
}
