package com.demo.gateway;

import static java.util.stream.Collectors.joining;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtService {

    @Value("${security.token.secret}")
    private String secret;
    
    public Optional<TokenInfo> extractInfo(String jwt) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT token = JWT.require(algorithm)
                .withIssuer("eventos")
                .build()
                .verify(jwt);
            String roles = extractRoles(token);
            return Optional.of(new TokenInfo(token.getSubject(), roles));
        } catch (JWTVerificationException ex) {
           log.error("Token inválido ou expirado", ex);
           return Optional.empty();
        }
    }

	private String extractRoles(DecodedJWT token) {
		return token.getClaim("roles")
            .asList(String.class).stream()
            .collect(joining(","));
	}
}
