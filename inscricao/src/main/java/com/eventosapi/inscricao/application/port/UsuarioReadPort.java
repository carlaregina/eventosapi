package com.eventosapi.inscricao.application.port;

import com.eventosapi.inscricao.domain.models.UsuarioResumo;
import java.util.Optional;

public interface UsuarioReadPort {
  Optional<UsuarioResumo> findById(Long id);
}