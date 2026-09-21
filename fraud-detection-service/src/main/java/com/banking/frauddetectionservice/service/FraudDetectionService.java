package com.banking.frauddetectionservice.service;

import java.util.Map;

public interface FraudDetectionService {
    public void checkTransaction(Map<String, Object> payload);
}
