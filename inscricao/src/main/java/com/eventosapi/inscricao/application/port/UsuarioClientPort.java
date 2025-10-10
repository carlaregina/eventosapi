package com.eventosapi.inscricao.application.port;

import java.util.Optional;

import com.eventosapi.inscricao.domain.models.Usuario;

public interface UsuarioClientPort {
  Optional<Usuario> findById(Long usuarioId);
}