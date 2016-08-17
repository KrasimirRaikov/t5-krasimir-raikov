package com.clouway.bank.core;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class BankOperationDTO {
    public final String amount;
    public final String operation;

    public BankOperationDTO(String amount, String operation) {
        this.amount = amount;
        this.operation = operation;
    }
}
