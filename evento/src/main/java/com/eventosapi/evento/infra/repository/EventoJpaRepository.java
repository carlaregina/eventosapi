package com.eventosapi.evento.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.eventosapi.evento.infra.entity.EventoEntity;

@Repository
public interface EventoJpaRepository extends JpaRepository<EventoEntity, Long>,
        JpaSpecificationExecutor<EventoEntity> {
}

