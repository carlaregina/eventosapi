package com.eventosapi.evento.application.port;

import com.eventosapi.evento.interfaces.dto.InscricaoDTO;

public interface EventoPublisherPort {
    void publicarEvento(InscricaoDTO dto);
}


