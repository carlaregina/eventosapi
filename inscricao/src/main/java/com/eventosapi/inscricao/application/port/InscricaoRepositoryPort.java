package com.eventosapi.inscricao.application.port;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.eventosapi.inscricao.domain.enums.StatusInscricao;
import com.eventosapi.inscricao.domain.models.Inscricao;

public interface InscricaoRepositoryPort {

  boolean existsByEventoAndUsuario(Long idEvento, Long idUsuario);

  long countConfirmadasByEvento(Long idEvento);

  Inscricao save(Inscricao inscricao);

  Optional<Inscricao> findById(Long id);

  List<Inscricao> findAll(Long idEvento,
                          Long idUsuario,
                          StatusInscricao status,
                          LocalDateTime dataInicio,
                          LocalDateTime dataFim,
                          int page,
                          int size);

  List<Inscricao> findByEventoAndStatus(Long eventoId, StatusInscricao status, Integer page, Integer size);

  void deleteById(Long id);  
}
