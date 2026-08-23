package com.banking.account_service.service;

/**
 * EventConsumer listens to events from Kafka and processes them
 * User does transaction → Kafka Topic → EventConsumer picks it up → Process logic
 *
 * You NEED EventConsumer if:
 *
 * You want to react to events (fraud detection, notifications, DB updates)
 * Services need to communicate asynchronously(Caller doesn't wait. It continues and gets notified later)
 */
public class AccountEventConsumer {
}
