package com.eventosapi.demo.services;

import com.eventosapi.demo.repositories.inscricaoRepository;

public class inscricaoService {
    private final inscricaoRepository inscricaoRepository;

    public inscricaoService(com.eventosapi.demo.repositories.inscricaoRepository inscricaoRepository) {
        this.inscricaoRepository = inscricaoRepository;
    }
}