package com.eventosapi.evento.application.port;

import com.eventosapi.evento.domain.model.Evento;
import com.eventosapi.evento.interfaces.dto.EventoResponseDTO;
import com.eventosapi.evento.interfaces.dto.InscricaoDTO;

public interface EventoPublisherPort {
    void publicarEvento(InscricaoDTO dto);
}


