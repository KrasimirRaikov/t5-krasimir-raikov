package com.clouway.bank.http;

import com.clouway.bank.guice.BankEventListener;
import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class Jetty {
    private Server server;

    /**
     * Jetty constructor to set up the servers port
     * and the database that the application uses
     *
     * @param port the port that the server uses
     */
    public Jetty(int port) {
        this.server = new Server(port);
    }

    /**
     * Starts the server
     */
    public synchronized void start() {
        WebAppContext webContext = new WebAppContext();
        webContext.setResourceBase("angular/src/main/webapp");
        webContext.setContextPath("/");
        webContext.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
        webContext.addServlet(DefaultServlet.class, "/");
        webContext.addEventListener(new BankEventListener());


        server.setHandler(webContext);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * stops the server
     */
    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
