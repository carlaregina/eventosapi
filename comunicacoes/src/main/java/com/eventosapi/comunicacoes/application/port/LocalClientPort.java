package com.eventosapi.comunicacoes.application.port;

import java.util.List;

import com.eventosapi.comunicacoes.domain.model.Local;

public interface LocalClientPort {
    List<Local> findAll();
    Local findById(Long id);
}