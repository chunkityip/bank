package service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class NotificationService {

    public void consumeOtpGenerated(@Payload Map<String, Object> payload) {
        try {
            String accountNumber = (String) payload.get("accountNumber");
            String transactionId = (String) payload.get("transactionId");
            String otp = (String) payload.get("otp");
            String amount = payload.get("amount").toString();
            String reason = (String) payload.get("reason");

            sendAlert(
                    "TRANSACTION VERIFICATION REQUIRED",
                    String.format("Suspicous activity detected on your account. " +
                                  "Reason: %s. " +
                                  "A transaction of amount %s is pending verification. " +
                                  "Your OTP is: %s. Valid for 5 minutes." +
                                  "If this was not you, please contact support immediately.", reason, amount, otp)
            );

        } catch (Exception e) {
            log.error("Error sending OTP notification: {}", e.getMessage());
        }
    }

}
