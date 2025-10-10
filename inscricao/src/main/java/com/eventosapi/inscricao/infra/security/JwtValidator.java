package com.eventosapi.inscricao.infra.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class JwtValidator  {

    @Value("${security.token.secret}")
    private String secret;

    @Value("${security.token.expiration-time-in-minutes}")
    private Integer expirationTimeInMinutes;

    public String validate(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                .withIssuer("eventos")
                .build()
                .verify(token)
                .getSubject();
        } catch (JWTVerificationException ex) {
           throw new RuntimeException("Token inválido ou expirado", ex);
        }
    }
    
}
