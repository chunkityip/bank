package com.banking.paymentservice.exception;

public class RazorpayException extends Exception {

    public RazorpayException(String message) {
        super(message);
    }

    public RazorpayException(String message, Throwable cause) {
        super(message, cause);
    }
}