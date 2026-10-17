package com.banking.account_service.exception;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(String accountNumber) {
        super("Insufficient funds for account " + accountNumber);
    }
}
