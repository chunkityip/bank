package com.banking.account_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * EventConsumer listens to events from Kafka and processes them
 * User does transaction → Kafka Topic → EventConsumer picks it up → Process logic
 *
 * You NEED EventConsumer if:
 *
 * You want to react to events (fraud detection, notifications, DB updates)
 * Services need to communicate asynchronously(Caller doesn't wait. It continues and gets notified later)
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AccountEventConsumer {

    private final AccountService accountService;

    @KafkaListener(topics = "transaction.completed")
    public void consumerTransactionCompleted(@Payload Map<String, Object> payload){
        try {
            String receiverAccount =  (String) payload.get("receiverAccount");
            BigDecimal amount =  new BigDecimal(payload.get("amount").toString());

            log.info("Crediting account: {}, amount: {}", receiverAccount, amount);
            accountService.creditBalance(receiverAccount, amount);
        } catch (Exception e) {
            log.error("Error while credit account: {}", e.getMessage());
        }
    }

    @KafkaListener(topics = "fraud.detected")
    public void consumerFraudDetected(@Payload Map<String, Object> payload){
        try {
            String accountNumber =  (String) payload.get("accountNumber");
            log.info("Fraud detected - blocking account: {}", accountNumber);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
