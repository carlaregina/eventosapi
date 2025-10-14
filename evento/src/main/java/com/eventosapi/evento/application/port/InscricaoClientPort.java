package com.eventosapi.evento.application.port;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eventosapi.evento.domain.model.Inscricao;

public interface InscricaoClientPort {
    List<Inscricao> findAllByEventoId(Long eventoId);
    Page<Inscricao> findAllByEventoId(Long eventoId, Pageable page);
}
