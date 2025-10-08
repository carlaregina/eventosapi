package com.eventosapi.interfaces.dtos;

import com.eventosapi.domain.enums.TipoUsuario;

public record UsuarioResponseDTO(
    String nome,

    String email,

    String telefone,

    TipoUsuario tipo
) {}