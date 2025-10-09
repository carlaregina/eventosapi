package com.eventosapi.inscricao.domain.models;

import java.time.LocalDateTime;
import com.eventosapi.inscricao.domain.enums.StatusInscricao;



public class Inscricao {
  private Long id;
  private Long eventoId;
  private Long usuarioId;
  private StatusInscricao status;
  private LocalDateTime data;

  public Inscricao(Long id, Long eventoId, Long usuarioId,
                   StatusInscricao status, LocalDateTime data) {
    this.id = id; this.eventoId = eventoId; this.usuarioId = usuarioId;
    this.status = status; this.data = data;
  }

  public static Inscricao nova(Long eventoId, Long usuarioId, StatusInscricao status, LocalDateTime data) {
    return new Inscricao(null, eventoId, usuarioId, status, data);
  }

  // comportamentos simples (ex.: mudança de status)
  public void alterarStatus(StatusInscricao novo) { this.status = novo; }

  // getters
  public Long getId() { return id; }
  public Long getEventoId() { return eventoId; }
  public Long getUsuarioId() { return usuarioId; }
  public StatusInscricao getStatus() { return status; }
  public LocalDateTime getData() { return data; }
  public void setId(Long id) { this.id = id; }
}