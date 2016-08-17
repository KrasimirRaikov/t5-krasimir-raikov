package com.clouway.bank.http;

import com.clouway.bank.core.*;
import com.google.gson.Gson;
import com.google.inject.Inject;

import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
@Singleton
public class DepositController extends HttpServlet {
    private final AccountRepository accountRepository;
    private final TransactionValidator validator;
    private final SessionProvider sessionProvider;
    private Gson gson;

    @Inject
    public DepositController(AccountRepository accountRepository, TransactionValidator validator, SessionProvider sessionProvider, Gson gson) {
        this.accountRepository = accountRepository;
        this.validator = validator;
        this.sessionProvider = sessionProvider;
        this.gson = gson;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        BankOperationDTO depositData = gson.fromJson(reader, BankOperationDTO.class);
        String message = "";
        if (depositData.amount == null || depositData.amount.isEmpty()) {
            message = "enter amount";
        }
        message += validator.validateAmount(depositData.amount);

        if (!"Deposit".equals(depositData.operation)) {
            message += "wrong operation";
        }

        if (message.isEmpty()) {
            Double amount = Double.parseDouble(depositData.amount);
            accountRepository.deposit(new Amount(sessionProvider.get().userId, amount));
            message = depositData.amount + " were deposited into your account";
        }

        response.setContentType("application/json");
        response.getWriter().println(gson.toJson(message));
    }

}
