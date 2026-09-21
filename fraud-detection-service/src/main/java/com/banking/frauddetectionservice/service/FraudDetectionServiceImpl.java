package com.banking.frauddetectionservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class FraudDetectionServiceImpl {

    public void checkTransaction(@Payload Map<String, Object> payload) {

    }
}
