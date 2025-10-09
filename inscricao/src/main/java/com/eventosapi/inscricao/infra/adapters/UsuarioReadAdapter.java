package com.eventosapi.inscricao.infra.adapters;

import com.eventosapi.inscricao.application.port.UsuarioClientPort;
import com.eventosapi.inscricao.domain.models.UsuarioResumo;
import com.eventosapi.inscricao.infra.repositories.UsuarioJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UsuarioReadAdapter implements UsuarioClientPort {
  private final UsuarioJpaRepository repo;

  @Override
  public Optional<UsuarioResumo> findById(Long id) {
    return repo.findById(id)
    .map(u -> new UsuarioResumo(u.getId(), u.getNome()));
  }
}
