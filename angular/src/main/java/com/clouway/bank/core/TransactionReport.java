package com.clouway.bank.core;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class TransactionReport {
  public final Double currentBalance;
  public final String message;

  public TransactionReport(Double currentBalance, String message) {
    this.currentBalance = currentBalance;
    this.message = message;
  }
}
