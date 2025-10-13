package com.eventosapi.auth.interfaces.rest.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventosapi.auth.application.services.AuthService;
import com.eventosapi.auth.interfaces.dtos.AuthRequestDTO;
import com.eventosapi.auth.interfaces.dtos.AuthResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação")
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Realizar login")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = AuthResponseDTO.class)) }),
        @ApiResponse(responseCode = "400", description = "Informações inválidas", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Map.class)) }),
        @ApiResponse(responseCode = "401", description = "Não autorizado", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Map.class)) }),
    })
    public ResponseEntity<AuthResponseDTO> autenticar(@Valid @RequestBody AuthRequestDTO request) {
        log.info("Iniciando processo de autenticação para o usuário: {}", request.getEmail());
        String token = authService.autenticar(request.toDomain());
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}
