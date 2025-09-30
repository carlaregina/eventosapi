package com.eventosapi.demo.services;

import com.eventosapi.demo.repositories.EventoRepository;
import org.springframework.stereotype.Service;

@Service
public class EventoService {
    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }
}