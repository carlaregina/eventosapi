package com.eventosapi.inscricao.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "inscricao",
  uniqueConstraints = @UniqueConstraint(name="uq_inscricao", columnNames={"id_evento","id_usuario"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Inscricao implements Serializable {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id_inscricao")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_evento", nullable=false, foreignKey=@ForeignKey(name="fk_inscricao_evento"))
  private Evento evento;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_usuario", nullable=false, foreignKey=@ForeignKey(name="fk_inscricao_usuario"))
  private Usuario usuario;

  @Column(name="data", nullable=false)
  private LocalDateTime data;

  @Enumerated(EnumType.STRING)
  @Column(name="status", nullable=false, length=32)
  private StatusInscricao status;

  @Version
  private Integer version;
}
