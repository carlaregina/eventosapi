package com.eventosapi.auth.application.services;

import org.springframework.stereotype.Service;

import com.eventosapi.auth.application.ports.AuthManagerPort;
import com.eventosapi.auth.application.ports.JwtBuilderPort;
import com.eventosapi.auth.domain.models.Conta;
import com.eventosapi.auth.domain.models.Usuario;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtBuilderPort jwtPort;
    private final AuthManagerPort authManagerPort;

    public String autenticar(Conta conta) {
        Usuario usuario = authManagerPort.autenticar(conta);
        return jwtPort.gerarToken(usuario);
    }
}
