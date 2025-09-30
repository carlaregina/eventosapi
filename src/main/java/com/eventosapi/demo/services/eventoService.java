package com.eventosapi.demo.services;

import com.eventosapi.demo.repositories.eventoRepository;
import org.springframework.stereotype.Service;

@Service
public class eventoService {
    private final eventoRepository eventoRepository;

    public eventoService(com.eventosapi.demo.repositories.eventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }
}