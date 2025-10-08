package com.eventosapi.inscricao.infra.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity @Table(name = "usuario")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UsuarioEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_usuario")
  private Long id;

  @Column(name = "nome", nullable = false)
  private String nome;
}