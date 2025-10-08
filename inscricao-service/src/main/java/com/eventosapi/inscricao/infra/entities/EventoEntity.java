package com.eventosapi.inscricao.infra.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity @Table(name = "evento")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EventoEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_evento")
  private Long id;

  @Column(name = "titulo", nullable = false)
  private String titulo;

  @Column(name = "max_participantes", nullable = false)
  private Integer maxParticipantes;
}
