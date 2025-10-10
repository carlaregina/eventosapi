package com.eventosapi.inscricao.infra.entities;

import com.eventosapi.inscricao.domain.enums.StatusInscricao;
import com.eventosapi.inscricao.domain.models.Inscricao;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(
  name = "inscricao", 
  uniqueConstraints = @UniqueConstraint(
    name = "uq_inscricao", columnNames = { "id_evento", "id_usuario" }))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InscricaoEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_inscricao")
  private Long id;

  @Column(name = "id_evento", nullable = false)
  private Long eventoId;

  @Column(name = "id_usuario", nullable = false)
  private Long usuarioId;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 255)
  private StatusInscricao status;

  @Column(name = "data", nullable = false)
  private LocalDateTime data;

  public static InscricaoEntity fromDomain(Inscricao inscricao) {
    return InscricaoEntity.builder()
      .id(inscricao.getId())
      .eventoId(inscricao.getEventoId())
      .usuarioId(inscricao.getUsuarioId())
      .status(inscricao.getStatus())
      .data(inscricao.getData())
      .build();
  }

  public Inscricao toDomain() {
    return new Inscricao(id, eventoId, usuarioId, status, data);
  }
}
