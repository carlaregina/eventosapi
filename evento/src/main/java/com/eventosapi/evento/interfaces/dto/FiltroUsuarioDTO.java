package com.eventosapi.evento.interfaces.dto;

import com.eventosapi.evento.domain.enums.TipoUsuario;
import lombok.Data;

@Data
public class FiltroUsuarioDTO {
    private String nome;

    private String email;

    private String telefone;

    private TipoUsuario tipo;
}