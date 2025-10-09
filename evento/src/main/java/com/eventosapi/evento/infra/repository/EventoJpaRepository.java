package com.eventosapi.evento.infra.repository;

import com.eventosapi.evento.domain.model.Evento;
import com.eventosapi.evento.infra.entity.EventoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventoJpaRepository extends JpaRepository<EventoEntity, Long>,
        JpaSpecificationExecutor<EventoEntity> {
    Optional<EventoEntity> findById(Long id);
}

