package com.eventosapi.inscricao.application.port;

import java.util.Optional;
import com.eventosapi.inscricao.domain.models.Evento;

public interface EventoClientPort {
    Optional<Evento> findById(Long id);
}