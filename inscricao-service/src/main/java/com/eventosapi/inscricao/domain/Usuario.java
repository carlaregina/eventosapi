package com.eventosapi.inscricao.domain;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.*;    

@Entity
@Table(name = "usuario")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Usuario implements Serializable {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id_usuario")
  private Long id;

  @Column(name="nome", nullable=false, length=255)
  private String nome;
}
