package com.banking.transactionservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger UI: http://localhost:8082/swagger-ui.html (direct)
 *          or http://localhost:8080/swagger-ui.html (all services, via API Gateway)
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Transaction Service API")
                        .description("Money transfers between accounts (Saga orchestrator), transaction history and OTP verification for transfers flagged by Fraud Detection.")
                        .version("v1"));
    }
}
