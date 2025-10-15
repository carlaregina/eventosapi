package com.eventosapi.comunicacoes.application.port;

import java.util.Optional;

import com.eventosapi.comunicacoes.domain.model.Evento;

public interface EventoClientPort {
    Optional<Evento> findById(Long id);
}
