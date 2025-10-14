package com.eventosapi.evento.application.port;

import java.util.List;
import java.util.Optional;

import com.eventosapi.evento.domain.model.Local;

public interface LocalClientPort {
    Boolean existsById(Long id);
    Optional<Local> findById(Long id);
}