package com.eventosapi.comunicacoes.application.port;

import java.util.List;

import com.eventosapi.comunicacoes.domain.model.Usuario;

public interface UsuarioClientPort {
    List<Usuario> findAll();
    Usuario findById(Long id);
}
