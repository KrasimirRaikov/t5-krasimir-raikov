package com.clouway.bank.core;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class SidFinder {


  public String findSid(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    String sessionId = null;

    if (cookies == null) {
      return null;
    }

    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("sessionId")) {
        sessionId = cookie.getValue();
      }

    }
    return sessionId;
  }
}
