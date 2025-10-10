package com.eventosapi.evento.application.port;

import java.util.List;

import com.eventosapi.evento.domain.model.Local;

public interface LocalClientPort {
    List<Local> findAll();

    Local findById(Long id);
}