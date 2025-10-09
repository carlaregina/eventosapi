package com.eventosapi.comunicacoes.application.port;


import com.eventosapi.comunicacoes.domain.model.Local;

import java.util.List;

public interface LocalClientPort {
    List<Local> findAll();

    Local findById(Long id);
}