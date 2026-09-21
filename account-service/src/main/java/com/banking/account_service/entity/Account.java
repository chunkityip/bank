package com.banking.account_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    private long id;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private String accountHolderName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    private AccountType accountType;

    @Column(nullable = false)
    private AccountStatus status;

    private BigDecimal balance;

    private BigDecimal dailyTransactionLimit;

    @CreationTimestamp 
    private LocalDateTime createdAt;

    @UpdateTimestamp 
    private LocalDateTime updatedAt;
}
