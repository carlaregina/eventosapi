package com.eventosapi.auth.domain.models;

public class Conta {
    private String email;
    private String senha;

    public Conta() {}

    public Conta(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }
}
