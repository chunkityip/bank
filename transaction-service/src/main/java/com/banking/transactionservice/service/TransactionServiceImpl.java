package com.banking.transactionservice.service;

import com.banking.transactionservice.client.AccountServiceClient;
import com.banking.transactionservice.dto.TransferRequest;
import com.banking.transactionservice.dto.TransferResponse;
import com.banking.transactionservice.entity.Transaction;
import com.banking.transactionservice.entity.TransactionStatus;
import com.banking.transactionservice.entity.TransactionType;
import com.banking.transactionservice.repository.TransactionRepository;
import event.TransactionInitiatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountServiceClient accountServiceClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TRANSACTION_INITIATED_TOPIC = "transaction.initiated";
    private static final String TRANSACTION_COMPLETED_TOPIC = "transaction.completed";
    private static final String TRANSACTION_REFUNDED_TOPIC = "transaction.refunded";

    @Override
    public TransferResponse transfer(TransferRequest request) {
        log.info("SAGA START ; Transfer: {} -> {} amount: {}",
                request.getSenderAccountNumber(),
                request.getReceiverAccountNumber(),
                request.getAmount());

        accountServiceClient.deductBalance(
                request.getSenderAccountNumber(),
                request.getAmount());

        Transaction transaction = new Transaction();
        transaction.setSenderAccountNumber(request.getSenderAccountNumber());
        transaction.setReceiverAccountNumber(request.getReceiverAccountNumber());
        transaction.setAmount(request.getAmount());
        transaction.setType(TransactionType.TRANSFER);
        transaction.setStatus(TransactionStatus.PROCESSING);
        transaction.setDescription(request.getDescription());
        transaction.setReferenceNumber(UUID.randomUUID().toString());

        Transaction savedTransaction = transactionRepository.save(transaction);
        log.info("Transaction saved as PROCESSING: {}", savedTransaction.getId());

        TransactionInitiatedEvent event = new TransactionInitiatedEvent(
                savedTransaction.getId(),
                savedTransaction.getSenderAccountNumber(),
                savedTransaction.getReceiverAccountNumber(),
                savedTransaction.getAmount(),
                savedTransaction.getDescription()
        );

        kafkaTemplate.send(TRANSACTION_INITIATED_TOPIC, savedTransaction.getId(), event);
        log.info("SAGA STEP2 - TransactionInitiatedEvent published: {}", savedTransaction.getId());

        return mapToResponse(savedTransaction);
    }

    @Override
    public TransferResponse getTransaction(String transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found: " + transactionId));
        return mapToResponse(transaction);
    }

    @Override
    public List<TransferResponse> getTransactionHistory(String accountNumber) {
        return transactionRepository.findAll().stream()
                .filter(transaction -> accountNumber.equals(transaction.getSenderAccountNumber())
                        || accountNumber.equals(transaction.getReceiverAccountNumber()))
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public TransferResponse verifyOTP(String transactionId, String otp) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found: " + transactionId));

        if (otp == null || otp.isBlank()) {
            throw new IllegalArgumentException("OTP is required");
        }

        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setCompletedAt(java.time.LocalDateTime.now());
        return mapToResponse(transactionRepository.save(transaction));
    }

    private TransferResponse mapToResponse(Transaction transaction) {
        TransferResponse response = new TransferResponse();
        response.setId(transaction.getId());
        response.setSenderAccountNumber(transaction.getSenderAccountNumber());
        response.setReceiverAccountNumber(transaction.getReceiverAccountNumber());
        response.setAmount(transaction.getAmount());
        response.setType(transaction.getType());
        response.setStatus(transaction.getStatus());
        response.setDescription(transaction.getDescription());
        response.setReferenceNumber(transaction.getReferenceNumber());
        response.setFailureReason(transaction.getFailureReason());
        response.setCreatedAt(transaction.getCreatedAt());
        response.setCompletedAt(transaction.getCompletedAt());

        return response;
    }
}
