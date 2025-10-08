package com.eventosapi.inscricao.application.port;

import com.eventosapi.inscricao.domain.enums.StatusInscricao;
import com.eventosapi.inscricao.domain.models.Inscricao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface InscricaoRepositoryPort {

  boolean existsByEventoAndUsuario(Long idEvento, Long idUsuario);

  //long countConfirmadasByEvento(Long idEvento);
//   @Query("select count(i) from InscricaoEntity i " +
//        "where i.eventoId = :eventoId and i.status = com.eventosapi.inscricao.domain.enums.StatusInscricao.CONFIRMADA")
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

  void deleteById(Long id);   // <-- mantenha este se o adapter implementa delete
}
