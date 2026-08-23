package com.banking.account_service.dto;

import com.banking.account_service.entity.AccountStatus;
import com.banking.account_service.entity.AccountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountResponse {

    private long id;

    private String accountNumber;

    private String accountHolderName;

    private String email;

    private String phone;

    private AccountType accountType;

    private AccountStatus status;

    private BigDecimal balance;

    private BigDecimal dailyTransactionLimit;

    private LocalDateTime createdAt;
}


