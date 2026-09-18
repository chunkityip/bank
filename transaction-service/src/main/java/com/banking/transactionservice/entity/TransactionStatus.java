package com.banking.transactionservice.entity;

/**
 * Transaction Lifecycle flow:
 *
 * PENDING -> PROCESSING -> COMPLETED
 * PENDING -> PROCESSING -> PENDING_VERIFICATION(suspicious detected) : it will either go COMPLETED(verified) or FLAGGED(SAGA REFUND
 * PENDING -> PROCESSING -> FAILED
 * PENDING -> PROCESSING -> FLAGGED
 */

public enum TransactionStatus {
    PENDING, PROCESSING, PENDING_VERIFICATION , COMPLETED, FAILED , FLAGGED
}
