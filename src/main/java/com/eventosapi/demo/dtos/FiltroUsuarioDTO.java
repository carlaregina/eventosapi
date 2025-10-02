package com.eventosapi.demo.dtos;

import com.eventosapi.demo.enums.TipoUsuario;
import lombok.Data;

@Data
public class FiltroUsuarioDTO {
    private String nome;

    private String email;

    private String telefone;

    private TipoUsuario tipo;
}