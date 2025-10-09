package com.eventosapi.interfaces.dtos;

import com.eventosapi.domain.enums.TipoUsuario;
import com.eventosapi.domain.models.Usuario;

public record UsuarioResponseDTO(
    String nome,

    String email,

    String telefone,

    TipoUsuario tipo
) {
    public static UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getTelefone(),
            usuario.getTipo()
        );
    }
}