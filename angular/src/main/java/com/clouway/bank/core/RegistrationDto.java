package com.clouway.bank.core;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class RegistrationDto {
  public final String username;
  public final String password;
  public final String confirmPassword;

  public RegistrationDto(String username, String password, String confirmPassword) {
    this.username = username;
    this.password = password;
    this.confirmPassword = confirmPassword;
  }
}
