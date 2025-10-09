package com.eventosapi.inscricao.application.port;

import com.eventosapi.inscricao.domain.models.EventoResumo;
import java.util.Optional;

public interface EventoClientPort {
  Optional<EventoResumo> findById(Long id);
}
