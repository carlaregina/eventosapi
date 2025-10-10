package com.eventosapi.inscricao.application.port;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eventosapi.inscricao.application.dtos.FiltroInscricaoDTO;
import com.eventosapi.inscricao.domain.models.Inscricao;

public interface InscricaoRepositoryPort {

  boolean existsByEventoAndUsuario(Long idEvento, Long idUsuario);

  long countConfirmadasByEvento(Long idEvento);

  Inscricao save(Inscricao inscricao);

  Optional<Inscricao> findById(Long id);
  
  Page<Inscricao> findAll(FiltroInscricaoDTO fitro, Pageable pageable);
  
}
