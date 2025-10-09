package com.eventosapi.evento.application.port;

import com.eventosapi.evento.domain.model.Evento;
import com.eventosapi.evento.interfaces.dto.EventoResponseDTO;

public interface EventoPublisherPort {
    void publicarEvento(EventoResponseDTO evento);
}


