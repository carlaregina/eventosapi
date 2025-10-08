package com.eventosapi.auth.interfaces.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import com.eventosapi.auth.application.services.AuthService;
import com.eventosapi.auth.domain.models.Conta;
import com.eventosapi.auth.interfaces.dtos.AuthRequestDTO;
import com.eventosapi.auth.interfaces.dtos.AuthResponseDTO;
import com.eventosapi.auth.interfaces.rest.controllers.AuthController;

public class AuthControllerTest {
    
    private AuthService authService;
    private AuthController authController;

    @BeforeEach
    void setUp() {
        this.authService = mock(AuthService.class);
        this.authController = new AuthController(authService);
    }

    @Test
    void deveAutenticarUsuario() {
        AuthRequestDTO request = new AuthRequestDTO("usuario@example.com", "senha123");
        when(authService.autenticar(any(Conta.class))).thenReturn("token123");

        ResponseEntity<AuthResponseDTO> response = authController.autenticar(request);

        assertEquals("token123", response.getBody().getToken());
        assertEquals(200, response.getStatusCode().value());
    }
}
