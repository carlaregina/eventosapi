package com.eventosapi.demo.repositories;

import com.eventosapi.demo.models.evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface eventoRepository extends JpaRepository<evento, Long> {

}