package com.eventosapi.comunicacoes.domain.model;

import com.eventosapi.comunicacoes.domain.enums.TipoUsuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private TipoUsuario tipo;
}

