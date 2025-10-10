package com.eventosapi.inscricao.domain.models;

public class Usuario {
  private final Long id;
  private final String nome;
  private final String email;

  public Usuario(Long id, String nome, String email) {
    this.id = id;
    this.nome = nome;
    this.email = email;
  }

  public Long getId() {
    return id;
  }

  public String getNome() {
    return nome;
  }

  public String getEmail() {
    return email;
  }
}