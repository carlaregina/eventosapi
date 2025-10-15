package com.eventosapi.comunicacoes.infra.adapters;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.eventosapi.comunicacoes.application.port.InscricaoClientPort;
import com.eventosapi.comunicacoes.domain.model.Inscricao;
import com.eventosapi.comunicacoes.infra.entities.InscricaoEntity;
import com.eventosapi.comunicacoes.infra.repositories.InscricaoJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class InscricaoClientAdapter implements InscricaoClientPort {

    private final InscricaoJpaRepository repository;

    @Override
    public Optional<Inscricao> findById(Long id) {
        try {
            return repository.findById(id).map(InscricaoEntity::toDomain);
        } catch (Exception e) {
            log.error("Erro ao buscar inscrição com id {}: {}", id, e.getMessage());
            return Optional.empty();
        }
    }
}

