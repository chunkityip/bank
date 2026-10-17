package com.banking.transactionservice.exception;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Turns exceptions into RFC 7807 ProblemDetail JSON so the frontend can tell errors apart:
 * { "status": 404, "title": "Not Found", "detail": "Transaction not found: ...", "instance": "/api/v1/transactions/..." }
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TransactionNotFoundException.class)
    public ProblemDetail handleNotFound(TransactionNotFoundException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    // Errors from Account Service (e.g. 404 account not found, 422 insufficient funds) are passed through
    // with their original status and message, instead of becoming a 500
    @ExceptionHandler(FeignException.class)
    public ProblemDetail handleAccountServiceError(FeignException e) {
        HttpStatus status = HttpStatus.resolve(e.status());
        if (status == null || !status.is4xxClientError()) {
            log.error("Account Service unavailable or failed", e);
            return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_GATEWAY, "Account Service is unavailable");
        }
        return ProblemDetail.forStatusAndDetail(status, extractDetail(e));
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpected(Exception e) {
        log.error("Unexpected error", e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred");
    }

    // @Valid failures: list each invalid field instead of the generic "Invalid request content."
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed");
        problem.setProperty("errors", errors);
        return ResponseEntity.badRequest().body(problem);
    }

    // Account Service replies with ProblemDetail JSON - reuse its "detail" message
    private String extractDetail(FeignException e) {
        try {
            JsonNode body = JsonMapper.shared().readTree(e.contentUTF8());
            if (body.hasNonNull("detail")) {
                return body.get("detail").asString();
            }
        } catch (Exception ignored) {
            // body was not JSON - fall through to the generic message
        }
        return "Account Service rejected the request";
    }
}
