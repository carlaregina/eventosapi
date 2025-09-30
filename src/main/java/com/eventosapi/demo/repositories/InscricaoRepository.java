package com.eventosapi.demo.repositories;

import com.eventosapi.demo.models.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscricaoRepository extends JpaRepository<Evento, Long> {
}