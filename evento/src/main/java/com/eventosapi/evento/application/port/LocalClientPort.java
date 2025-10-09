package com.eventosapi.evento.application.port;

import com.eventosapi.evento.domain.model.Local;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface LocalClientPort {
    List<Local> findAll();

    Local findById(Long id);
}