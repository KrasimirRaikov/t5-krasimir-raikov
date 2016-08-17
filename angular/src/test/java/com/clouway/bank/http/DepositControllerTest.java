package com.clouway.bank.http;

import com.clouway.bank.core.*;
import com.google.gson.Gson;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class DepositControllerTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock
    AccountRepository repository;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    TransactionValidator validator;
    @Mock
    SessionProvider sessionProvider;

    private DepositController depositController;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new Gson();
        depositController = new DepositController(repository, validator, sessionProvider, gson);
    }

    @Test
    public void depositFunds() throws ServletException, IOException, ValidationException {
        String jsonBytes = "{amount: '12.5', operation: 'Deposit'}";
        Reader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(jsonBytes.getBytes())));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);

        Session session = new Session("1", "Ivan", 123456789L);
        context.checking(new Expectations() {{
            oneOf(request).getReader();
            will(returnValue(reader));

            oneOf(sessionProvider).get();
            will(returnValue(session));

            oneOf(validator).validateAmount("12.5");
            will(returnValue(""));

            oneOf(repository).deposit(new Amount("Ivan", 12.5));
            will(returnValue(12.5));

            oneOf(response).setContentType("text/plain");

            oneOf(response).getWriter();
            will(returnValue(writer));
        }});

        depositController.init();
        depositController.doPost(request, response);

        writer.flush();
        String message = out.toString();
        assertThat(message, is(equalTo("12.5 were deposited into your account\n")));
    }

    @Test
    public void missingAmount() throws IOException, ServletException {
        String jsonBytes = "{operation: 'Deposit'}";
        Reader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(jsonBytes.getBytes())));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);

        context.checking(new Expectations() {{
            oneOf(request).getReader();
            will(returnValue(reader));

            oneOf(validator).validateAmount(null);
            will(returnValue(""));

            oneOf(response).setContentType("text/plain");

            oneOf(response).getWriter();
            will(returnValue(writer));
        }});

        depositController.init();
        depositController.doPost(request, response);

        writer.flush();
        String message = out.toString();
        assertThat(message, is(equalTo("enter amount\n")));
    }

    @Test
    public void invalidAmount() throws IOException, ServletException {
        String jsonBytes = "{amount: '12.5s', operation: 'Deposit'}";
        Reader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(jsonBytes.getBytes())));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);


        context.checking(new Expectations() {{
            oneOf(request).getReader();
            will(returnValue(reader));

            oneOf(validator).validateAmount("12.5s");
            will(returnValue("invalid amount"));

            oneOf(response).setContentType("text/plain");

            oneOf(response).getWriter();
            will(returnValue(writer));
        }});

        depositController.init();
        depositController.doPost(request, response);

        writer.flush();
        String message = out.toString();
        assertThat(message, is(equalTo("invalid amount\n")));
    }

    @Test
    public void invalidOperation() throws Exception {
        String jsonBytes = "{amount: '12.5s', operation: 'reposit'}";
        Reader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(jsonBytes.getBytes())));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);


        context.checking(new Expectations() {{
            oneOf(request).getReader();
            will(returnValue(reader));

            oneOf(validator).validateAmount("12.5s");
            will(returnValue(""));

            oneOf(response).setContentType("text/plain");

            oneOf(response).getWriter();
            will(returnValue(writer));
        }});

        depositController.init();
        depositController.doPost(request, response);

        writer.flush();
        String message = out.toString();
        assertThat(message, is(equalTo("wrong operation\n")));
    }
}