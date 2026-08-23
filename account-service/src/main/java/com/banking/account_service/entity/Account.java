package com.banking.account_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Account {

    @Entity
    @Table(name = "accounts")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Accounts
    {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @Column(nullable = false, unique = true)
        private String accountNumber;

        @Column(nullable = false)
        private String accountHolderName;

        @Column(nullable = false)
        private String email;

        @Column(nullable = false)
        private String phone;

        @Enumerated(EnumType.STRING)
        private AccountType accountType;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private AccountStatus status;

        @Column(nullable = false, precision = 15, scale = 2)
        private BigDecimal balance;

        @Column(nullable = false, precision = 15, scale = 2)
        private BigDecimal dailyTransactionLimit;

        @CreationTimestamp
        private LocalDateTime createdAt;

        @UpdateTimestamp
        private LocalDateTime updatedAt;
    }

}
