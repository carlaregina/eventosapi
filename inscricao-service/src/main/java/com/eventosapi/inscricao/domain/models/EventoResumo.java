package com.eventosapi.inscricao.domain.models;

public class EventoResumo {
  private final Long id;
  private final String titulo;
  private final Integer maxParticipantes;

  public EventoResumo(Long id, String titulo, Integer maxParticipantes) {
    this.id = id; this.titulo = titulo; this.maxParticipantes = maxParticipantes;
  }
  public Long getId() { return id; }
  public String getTitulo() { return titulo; }
  public Integer getMaxParticipantes() { return maxParticipantes; }
}