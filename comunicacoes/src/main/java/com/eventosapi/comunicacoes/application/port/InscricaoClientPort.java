package com.eventosapi.comunicacoes.application.port;

import com.eventosapi.comunicacoes.domain.model.Inscricao;

import java.util.List;

public interface InscricaoClientPort {
//    List<Inscricao> findAll();

    Inscricao findById(Long id);
}

