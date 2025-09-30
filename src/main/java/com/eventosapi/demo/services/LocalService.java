package com.eventosapi.demo.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventosapi.demo.models.Local;
import com.eventosapi.demo.repositories.LocalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocalService {

    private final LocalRepository localRepository;

    @Transactional(readOnly = true)
    public Page<Local> listar(Pageable pageable) {
        return localRepository.findAll(pageable);
    }
}