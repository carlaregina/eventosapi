package com.eventosapi.auth.domain.models;

import com.eventosapi.auth.domain.enums.TipoUsuario;

public class Usuario {

    private String id;
    private String email;
    private String senha;
    private TipoUsuario tipo;

    public Usuario() {}

    public Usuario(String id, String email, String senha, TipoUsuario tipo) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }
}
