package com.eventosapi.domain.models;

import com.eventosapi.domain.enums.TipoUsuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String senha;
    private TipoUsuario tipo;
}