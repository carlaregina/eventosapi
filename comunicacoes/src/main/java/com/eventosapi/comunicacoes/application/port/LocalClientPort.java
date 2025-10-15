package com.eventosapi.comunicacoes.application.port;

import java.util.Optional;

import com.eventosapi.comunicacoes.domain.model.Local;

public interface LocalClientPort {
    Optional<Local> findById(Long id);
}
