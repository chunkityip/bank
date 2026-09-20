package com.banking.account_service.controller;

import com.banking.account_service.dto.AccountResponse;
import com.banking.account_service.dto.CreateAccountRequest;
import com.banking.account_service.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
@Slf4j
@RequiredArgsConstructor
public class AccountControllerImpl implements AccountController {
    private final AccountService accountService;

    @Override
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(request));
    }

    @Override
    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.getAccount(accountNumber));
    }

    @Override
    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.getBalance(accountNumber));
    }

    @Override
    @PutMapping("/{accountNumber}/block")
    public ResponseEntity<String> blockAccount(@PathVariable String accountNumber) {
        accountService.blockAccount(accountNumber);
        return ResponseEntity.ok("Account blocked Successfully");
    }

    //Step1 : Deduct Balance Called By Transaction Service when transfer is initiated
    @Override
    @PutMapping("/{accountNumber}/deduct")
    public ResponseEntity<String> deductBalance(@PathVariable String accountNumber, @RequestParam BigDecimal amount) {
        accountService.deductBalance(accountNumber, amount);
        return ResponseEntity.ok("Account deducted Successfully");
    }

    /**
    Step4 : Compensating transaction endpoint Called By Transaction Service in Two Scenarios:
            1. Fraud Detected -> refund sender (undo step 1)
            2. Transaction completed -> Credit receiver
     */
    @Override
    @PutMapping("/{accountNumber}/credit")
    public ResponseEntity<String> creditBalance(@PathVariable String accountNumber, @RequestParam BigDecimal amount) {
        accountService.creditBalance(accountNumber, amount);
        return ResponseEntity.ok("Balance Credited Successfully");
    }
}
