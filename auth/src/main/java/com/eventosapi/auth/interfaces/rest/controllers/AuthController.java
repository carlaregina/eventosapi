package com.eventosapi.auth.interfaces.rest.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventosapi.auth.application.services.AuthService;
import com.eventosapi.auth.interfaces.dtos.AuthRequestDTO;
import com.eventosapi.auth.interfaces.dtos.AuthResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> autenticar(@Valid @RequestBody AuthRequestDTO request) {
        String token = authService.autenticar(request.toDomain());
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}
