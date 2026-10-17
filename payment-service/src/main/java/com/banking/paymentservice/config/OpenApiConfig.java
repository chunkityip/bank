package com.banking.paymentservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger UI: http://localhost:8085/swagger-ui.html (direct)
 *          or http://localhost:8080/swagger-ui.html (all services, via API Gateway)
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Payment Service API")
                        .description("Create Razorpay payment orders and receive Razorpay webhooks (payment.captured / payment.failed).")
                        .version("v1"));
    }
}
