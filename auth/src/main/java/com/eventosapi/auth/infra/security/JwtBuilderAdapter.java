package com.eventosapi.auth.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.eventosapi.auth.application.ports.JwtBuilderPort;
import com.eventosapi.auth.domain.models.Usuario;

@Service
public class JwtBuilderAdapter implements JwtBuilderPort {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.expiration-time-in-minutes}")
    private Integer expirationTimeInMinutes;

    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                .withIssuer("eventos")
                .withSubject(usuario.getEmail())
                .withExpiresAt(generateExpirationDate())
                .sign(algorithm);
            return token;
        } catch (JWTCreationException ex) {
            throw new RuntimeException("Erro ao gerar token", ex);
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now()
            .plusMinutes(expirationTimeInMinutes)
            .toInstant(ZoneOffset.of("-03:00"));
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                .withIssuer("eventos")
                .build()
                .verify(token)
                .getSubject();
        } catch (JWTVerificationException ex) {
           return "";
        }
    }
    
}
