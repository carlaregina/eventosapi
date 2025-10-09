package com.eventosapi.inscricao.application.port;

import com.eventosapi.inscricao.domain.enums.StatusInscricao;
import com.eventosapi.inscricao.domain.models.Inscricao;
import com.eventosapi.inscricao.infra.entities.InscricaoEntity;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

  List<Inscricao> findByEventoAndStatus(Long eventoId, StatusInscricao status,
                                        Integer page, Integer size);

  void deleteById(Long id);  
}
