package com.eventosapi.comunicacoes.infra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventosapi.comunicacoes.infra.entities.InscricaoEntity;

public interface InscricaoJpaRepository extends JpaRepository<InscricaoEntity, Long> {
}