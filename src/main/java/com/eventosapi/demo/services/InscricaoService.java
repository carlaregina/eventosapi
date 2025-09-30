package com.eventosapi.demo.services;

import com.eventosapi.demo.repositories.InscricaoRepository;

public class InscricaoService {
    private final InscricaoRepository inscricaoRepository;

    public InscricaoService(InscricaoRepository inscricaoRepository) {
        this.inscricaoRepository = inscricaoRepository;
    }
}