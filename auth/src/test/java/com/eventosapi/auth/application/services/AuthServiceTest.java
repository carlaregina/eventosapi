package com.eventosapi.auth.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;

import com.eventosapi.auth.application.ports.AuthManagerPort;
import com.eventosapi.auth.application.ports.JwtBuilderPort;
import com.eventosapi.auth.domain.models.Conta;
import com.eventosapi.auth.domain.models.Usuario;

public class AuthServiceTest {
    
    private JwtBuilderPort jwtBuilder;
    private AuthManagerPort authManager;

    private AuthService authService;

    @BeforeEach
    void setup() {
        jwtBuilder = mock(JwtBuilderPort.class);
        authManager = mock(AuthManagerPort.class);
        authService = new AuthService(jwtBuilder, authManager);
    }

    @Test
    void deveAutenticarUsuario() {
        Conta conta = new Conta("usuario", "senha");
        Usuario usuario = new Usuario();

        when(authManager.autenticar(conta)).thenReturn(usuario);
        when(jwtBuilder.gerarToken(usuario)).thenReturn("token");

        String token = authService.autenticar(conta);

        assertEquals("token", token);
        verify(authManager, times(1)).autenticar(any(Conta.class));
        verify(jwtBuilder, times(1)).gerarToken(any(Usuario.class));
    }

    @Test
    void deveLancarExcecaoQuandoNaoAutenticarUsuario() {
        Conta conta = new Conta("usuario", "senha");
        Usuario usuario = new Usuario();

        when(authManager.autenticar(conta)).thenThrow(BadCredentialsException.class);
        when(jwtBuilder.gerarToken(usuario)).thenReturn("token");

        assertThrows(BadCredentialsException.class, () -> authService.autenticar(conta));
    }

    @Test
    void deveLancarExcecaoQuandoNaoGerarToken() {
        Conta conta = new Conta("usuario", "senha");
        Usuario usuario = new Usuario();

        when(authManager.autenticar(conta)).thenReturn(usuario);
        when(jwtBuilder.gerarToken(usuario)).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> authService.autenticar(conta));
    }
}
