package com.eventosapi.comunicacoes.application.port;

import com.eventosapi.comunicacoes.domain.model.Inscricao;

public interface InscricaoClientPort {
    Inscricao findById(Long id);
}

