package com.eventosapi.inscricao.infra.adapters;

import com.eventosapi.inscricao.application.port.EventoClientPort;
import com.eventosapi.inscricao.domain.models.EventoResumo;
import com.eventosapi.inscricao.infra.repositories.EventoJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class EventoReadAdapter implements EventoClientPort {
  private final EventoJpaRepository repo;

  @Override
  public Optional<EventoResumo> findById(Long id) {
    return repo.findById(id)
    .map(e -> new EventoResumo(e.getId(), e.getTitulo(), e.getMaxParticipantes()));
  }
}