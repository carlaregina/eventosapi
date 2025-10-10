package com.eventosapi.inscricao.domain.models;

import java.time.LocalDateTime;

import com.eventosapi.inscricao.domain.enums.StatusInscricao;

public class Inscricao {
  private Long id;
  private Long eventoId;
  private Long usuarioId;
  private StatusInscricao status;
  private LocalDateTime data;

  public Inscricao(Long id, Long eventoId, Long usuarioId, StatusInscricao status, LocalDateTime data) {
    this.id = id;
    this.eventoId = eventoId;
    this.usuarioId = usuarioId;
    this.status = status;
    this.data = data;
  }

  public void alterarStatus(StatusInscricao novo) {
    this.status = novo;
  }

  public Long getId() {
    return id;
  }

  public Long getEventoId() {
    return eventoId;
  }

  public Long getUsuarioId() {
    return usuarioId;
  }

  public StatusInscricao getStatus() {
    return status;
  }

  public LocalDateTime getData() {
    return data;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setStatus(StatusInscricao status) {
    this.status = status;
  }
}