package com.eventosapi.inscricao.domain;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "evento")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Evento implements Serializable {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id_evento")
  private Long id;

  @Column(name="titulo", nullable=false, length=255)
  private String titulo;

  @Column(name="max_participantes", nullable=false)
  private Integer maxParticipantes;

  // campos a mais podem existir na tabela, não tem problema se não mapear aqui
}
