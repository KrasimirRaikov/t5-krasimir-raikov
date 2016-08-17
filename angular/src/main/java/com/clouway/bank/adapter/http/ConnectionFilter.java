package com.clouway.bank.adapter.http;

import com.google.inject.Singleton;
import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Provides connections to the ConnectionProvider
 *
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
@Singleton
public class ConnectionFilter implements Filter {
  private static ThreadLocal<Connection> connections = new ThreadLocal<Connection>();
  private MysqlConnectionPoolDataSource connectionPool;

  /**
   * Gives the connection for the database
   *
   * @return connection to the database
   */
  public static Connection getConnection() {
    return connections.get();
  }

  /**
   * destroying the Filter
   */
  public void destroy() {
  }

  /**
   * Sets the ThreadLocal connectino to the database
   *
   * @param req   request
   * @param resp  response
   * @param chain filter chain
   * @throws ServletException
   * @throws IOException
   */
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

    try {
      Connection connection = connectionPool.getConnection();
      connections.set(connection);
      chain.doFilter(req, resp);
      connection.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  /**
   * initializes the filter, the connection pool and the ThreadLocal connection
   *
   * @param config filter configuration
   * @throws ServletException
   */
  public void init(FilterConfig config) throws ServletException {
    connectionPool = new MysqlConnectionPoolDataSource();
    connectionPool.setURL("jdbc:mysql://localhost:3306/bank?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
    connectionPool.setUser("root");
    connectionPool.setPassword("clouway.com");

    try {
      Connection connection = connectionPool.getConnection();
      connections.set(connection);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}

