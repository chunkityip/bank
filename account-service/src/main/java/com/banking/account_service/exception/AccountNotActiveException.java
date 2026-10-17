package com.banking.account_service.exception;

public class AccountNotActiveException extends RuntimeException {

    public AccountNotActiveException(String accountNumber) {
        super("Account is not active: " + accountNumber);
    }
}
