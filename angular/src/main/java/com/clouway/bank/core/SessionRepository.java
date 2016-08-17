package com.clouway.bank.core;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public interface SessionRepository {

  /**
   * Creates a session into the repository
   *
   * @param session the session to be added
   */
  void create(Session session);

  /**
   * Retrieves session from the repository by given id
   *
   * @param sessionId the id of the login
   * @return the Session
   */
  Session retrieve(String sessionId);

  /**
   * Updates the session by it's id
   *
   * @param session the updated version of the session
   */
  void update(Session session);
}
