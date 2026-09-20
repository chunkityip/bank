package com.banking.account_service.controller;

import com.banking.account_service.dto.AccountResponse;
import com.banking.account_service.dto.CreateAccountRequest;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface AccountController {
    ResponseEntity<AccountResponse> createAccount(CreateAccountRequest request);

    ResponseEntity<AccountResponse> getAccount(String accountNumber);

    ResponseEntity<BigDecimal> getBalance(String accountNumber);

    ResponseEntity<String> blockAccount(String accountNumber);

    ResponseEntity<String> deductBalance(String accountNumber, BigDecimal amount);

    ResponseEntity<String> creditBalance(String accountNumber, BigDecimal amount);
}
