package com.eventosapi.inscricao.domain.models;

public class UsuarioResumo {
  private final Long id;
  private final String nome;

  public UsuarioResumo(Long id, String nome) {
    this.id = id;
    this.nome = nome;
  }

  public Long getId() {
    return id;
  }

  public String getNome() {
    return nome;
  }
}