package com.banking.account_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger UI: http://localhost:8081/swagger-ui.html (direct)
 *          or http://localhost:8080/swagger-ui.html (all services, via API Gateway)
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Account Service API")
                        .description("Create and look up bank accounts, check balances, block accounts. Deduct/credit endpoints are called internally by Transaction Service during a transfer.")
                        .version("v1"));
    }
}
