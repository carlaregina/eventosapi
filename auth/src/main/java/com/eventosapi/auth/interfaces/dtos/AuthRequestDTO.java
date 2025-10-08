package com.eventosapi.auth.interfaces.dtos;

import com.eventosapi.auth.domain.models.Conta;

import lombok.Data;

@Data
public class AuthRequestDTO {
    
    private String email;
    private String senha;

    public Conta toDomain() {
        return new Conta(email, senha);
    }
}
