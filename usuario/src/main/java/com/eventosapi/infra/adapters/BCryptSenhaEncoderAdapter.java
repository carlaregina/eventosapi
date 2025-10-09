package com.eventosapi.infra.adapters;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.eventosapi.application.port.SenhaEncoderPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BCryptSenhaEncoderAdapter implements SenhaEncoderPort {

    private final PasswordEncoder encoder;

    @Override
    public String encode(String senha) {
        return encoder.encode(senha);
    }
}