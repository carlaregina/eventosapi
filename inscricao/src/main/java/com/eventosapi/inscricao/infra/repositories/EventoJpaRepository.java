package com.eventosapi.inscricao.infra.repositories;

import com.eventosapi.inscricao.infra.entities.EventoEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventoJpaRepository extends JpaRepository<EventoEntity, Long> {}
