package com.eventosapi.comunicacoes.application.port;

import com.eventosapi.comunicacoes.domain.model.Evento;

public interface EventoClientPort {
    Evento findById(Long id);

}
