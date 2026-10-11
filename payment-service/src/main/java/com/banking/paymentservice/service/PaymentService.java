package com.banking.paymentservice.service;

import com.banking.paymentservice.dto.CreatePaymentOrderResponse;
import com.banking.paymentservice.dto.CreatePaymentRequest;
import com.razorpay.RazorpayException;

import java.util.Map;

public interface PaymentService {

    CreatePaymentOrderResponse createPaymentOrder(CreatePaymentRequest request) throws RazorpayException;

    void handleWebhook(Map<String, Object> payload);

}