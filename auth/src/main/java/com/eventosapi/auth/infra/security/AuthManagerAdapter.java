package com.eventosapi.auth.infra.security;

import com.eventosapi.auth.application.ports.AuthManagerPort;
import com.eventosapi.auth.domain.models.Conta;
import com.eventosapi.auth.domain.models.Usuario;
import com.eventosapi.auth.infra.entities.UsuarioEntity;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthManagerAdapter implements AuthManagerPort {

    private final AuthenticationManager authenticationManager;

    @Override
    public Usuario autenticar(Conta conta) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(conta.getEmail(), conta.getSenha());
        Authentication authentication = authenticationManager.authenticate(token);
        return ((UsuarioEntity) authentication.getPrincipal()).toDomain();
    }

}
