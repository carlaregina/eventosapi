package com.eventosapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "local-microservice-api",
        version = "1.0",
        description = "Sistema de gerenciamento de locais."
    )
)
public class OpenAPIConfig {
}
