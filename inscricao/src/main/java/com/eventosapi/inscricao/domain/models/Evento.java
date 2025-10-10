package com.eventosapi.inscricao.domain.models;

public class Evento {
  private Long id;
  private String titulo;
  private Integer maxParticipantes;

  public Evento() {
    this.id = null;
    this.titulo = null;
    this.maxParticipantes = null;
  }

  public Evento(Long id, String titulo, Integer maxParticipantes) {
    this.id = id;
    this.titulo = titulo;
    this.maxParticipantes = maxParticipantes;
  }

  public Long getId() {
	return id;
  }

  public void setId(Long id) {
	this.id = id;
  }

  public String getTitulo() {
	return titulo;
  }

  public void setTitulo(String titulo) {
	this.titulo = titulo;
  }

  public Integer getMaxParticipantes() {
	return maxParticipantes;
  }

  public void setMaxParticipantes(Integer maxParticipantes) {
	this.maxParticipantes = maxParticipantes;
  }
  
}