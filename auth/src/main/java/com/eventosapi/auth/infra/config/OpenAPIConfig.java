package com.eventosapi.auth.infra.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "auth-api",
        version = "1.0",
        description = "Sistema de autenticação."
    )
)
public class OpenAPIConfig {
}
