package com.eventosapi.comunicacoes.application.port;

import java.util.List;
import java.util.Optional;

import com.eventosapi.comunicacoes.domain.model.Usuario;

public interface UsuarioClientPort {
    List<Usuario> findAll();
    Optional<Usuario> findById(Long id);
}
