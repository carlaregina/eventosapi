package com.eventosapi.demo.services;

import com.eventosapi.demo.repositories.LocalRepository;
import org.springframework.stereotype.Service;

@Service
public class LocalService {
    private final LocalRepository localRepository;

    public LocalService(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }
}