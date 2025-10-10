package com.eventosapi.inscricao.application.port;

import com.eventosapi.inscricao.domain.models.Evento;
import java.util.Optional;

public interface EventoClientPort {
  Optional<Evento> findById(Long id);
}
