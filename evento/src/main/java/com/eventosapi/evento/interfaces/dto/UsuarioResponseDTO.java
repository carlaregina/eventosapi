package com.eventosapi.evento.interfaces.dto;

import com.eventosapi.evento.domain.enums.TipoUsuario;

public record UsuarioResponseDTO(
        String nome,

        String email,

        String telefone,

        TipoUsuario tipo
) {}