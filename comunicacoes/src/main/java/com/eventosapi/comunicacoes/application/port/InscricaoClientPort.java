package com.eventosapi.comunicacoes.application.port;

import java.util.Optional;

import com.eventosapi.comunicacoes.domain.model.Inscricao;

public interface InscricaoClientPort {
    Optional<Inscricao> findById(Long id);
}

