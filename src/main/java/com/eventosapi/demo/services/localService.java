package com.eventosapi.demo.services;

import com.eventosapi.demo.repositories.localRepository;
import org.springframework.stereotype.Service;

@Service
public class localService {
    private final localRepository localRepository;

    public localService(com.eventosapi.demo.repositories.localRepository localRepository) {
        this.localRepository = localRepository;
    }
}