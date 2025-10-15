package com.eventosapi.evento.application.port;

import com.eventosapi.evento.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioClientPort {
    List<Usuario> findAll();
    Boolean existsById(Long id);
    Optional<Usuario> findById(Long id);
}
