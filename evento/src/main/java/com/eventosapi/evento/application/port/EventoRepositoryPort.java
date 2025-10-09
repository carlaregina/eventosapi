package com.eventosapi.evento.application.port;

import com.eventosapi.evento.domain.model.Evento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepositoryPort {
    Evento findById(Long id);

    Page<Evento> findAll(@Nullable Specification<Evento> spec, Pageable pageable);


    Evento save(Evento evento);

    void delete(Evento evento);
}
