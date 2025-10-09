package com.eventosapi.evento.application.port;

import com.eventosapi.evento.domain.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UsuarioClientPort {
    List<Usuario> findAll();

    Usuario findById(Long id);
}
