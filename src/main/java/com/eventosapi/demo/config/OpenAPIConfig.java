package com.eventosapi.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "eventos-api",
        version = "1.0",
        description = "Sistema de gerenciamento de eventos."
    )
)
public class OpenAPIConfig {
}
