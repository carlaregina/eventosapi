package com.eventosapi.comunicacoes.application.port;



import com.eventosapi.comunicacoes.domain.model.Usuario;

import java.util.List;

public interface UsuarioClientPort {
    List<Usuario> findAll();

    Usuario findById(Long id);
}
