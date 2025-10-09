package com.eventosapi.infra.adapters;

import org.springframework.context.annotation.Bean;

import com.eventosapi.application.port.SenhaEncodePort;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptSenhaEncoderAdapter implements SenhaEncodePort {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public BCryptSenhaEncoderAdapter() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encode(String senha) {
        return bCryptPasswordEncoder.encode(senha);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return bCryptPasswordEncoder;
    }
    
}