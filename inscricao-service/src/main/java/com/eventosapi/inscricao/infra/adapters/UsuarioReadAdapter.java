package com.eventosapi.inscricao.infra.adapters;

import com.eventosapi.inscricao.application.port.UsuarioReadPort;
import com.eventosapi.inscricao.domain.models.UsuarioResumo;
import com.eventosapi.inscricao.infra.repositories.UsuarioJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UsuarioReadAdapter implements UsuarioReadPort {
  private final UsuarioJpaRepository repo;

  @Override
  public Optional<UsuarioResumo> findById(Long id) {
    return repo.findById(id)
    .map(u -> new UsuarioResumo(u.getId(), u.getNome()));
  }
}
