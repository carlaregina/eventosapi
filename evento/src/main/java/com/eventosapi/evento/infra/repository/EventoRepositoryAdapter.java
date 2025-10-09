package com.eventosapi.evento.infra.repository;

import com.eventosapi.evento.application.port.EventoRepositoryPort;
import com.eventosapi.evento.domain.model.Evento;
import com.eventosapi.evento.infra.entity.EventoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public class EventoRepositoryAdapter implements EventoRepositoryPort {
    private final EventoJpaRepository jpa;


    public EventoRepositoryAdapter(EventoJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Evento findById(Long id) {
        return jpa.findById(id)
                .map(EventoEntity::toDomain)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado com id: " + id));
    }

    @Override
    public Page<Evento> findAll(Specification<Evento> spec, Pageable pageable) {
        Page<EventoEntity> pageEntity = jpa.findAll((Specification) spec, pageable);
        return pageEntity.map(EventoEntity::toDomain);
    }


    @Override
    public Evento save(Evento evento) {
        EventoEntity entity = EventoEntity.fromDomain(evento);
        EventoEntity saved = jpa.save(entity);
        return saved.toDomain();
    }

    @Override
    public void delete(Evento evento) {
        EventoEntity entity = EventoEntity.fromDomain(evento);
        jpa.delete(entity);
    }
}
