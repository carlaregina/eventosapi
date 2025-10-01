package com.eventosapi.demo.dtos;

import com.eventosapi.demo.enums.TipoUsuario;

public record UsuarioResponseDTO(
    String nome,

    String email,

    String telefone,

    TipoUsuario tipo
) {}