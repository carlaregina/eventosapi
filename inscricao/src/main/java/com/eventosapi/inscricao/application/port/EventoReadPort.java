package com.eventosapi.inscricao.application.port;

import com.eventosapi.inscricao.domain.models.EventoResumo;
import java.util.Optional;

public interface EventoReadPort {
  Optional<EventoResumo> findById(Long id);
}
