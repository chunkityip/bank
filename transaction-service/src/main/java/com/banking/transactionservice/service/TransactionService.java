package com.banking.transactionservice.service;

import com.banking.transactionservice.dto.TransferRequest;
import com.banking.transactionservice.dto.TransferResponse;

import java.util.List;

public interface TransactionService {

    TransferResponse transfer(TransferRequest request);

    TransferResponse getTransaction(String transactionId);

    List<TransferResponse> getTransactionHistory(String accountNumber);

    TransferResponse verifyOTP(String transactionId, String otp);
}
