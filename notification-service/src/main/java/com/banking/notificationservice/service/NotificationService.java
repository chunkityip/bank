package com.banking.notificationservice.service;

import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

public interface NotificationService {
    void consumeOtpGenerated(Map<String, Object> payload);
    void consumeTransactionCompleted(Map<String, Object> payload);
    void sendAlert(String accountNumber, String subject, String message);
}
