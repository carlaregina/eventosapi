package com.eventosapi.inscricao.repository;

import com.eventosapi.inscricao.domain.Evento;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventoReadRepository extends JpaRepository<Evento, Long> {}
