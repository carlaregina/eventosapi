package com.eventosapi.demo.services;

import com.eventosapi.demo.repositories.usuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class usuarioService {
    private final usuarioRepository usuarioRepository;

    public usuarioService(com.eventosapi.demo.repositories.usuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
}