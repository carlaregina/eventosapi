package com.eventosapi.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.eventosapi.domain.enums.TipoUsuario;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private TipoUsuario tipo;
}