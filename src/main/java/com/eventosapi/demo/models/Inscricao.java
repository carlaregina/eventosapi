package com.eventosapi.demo.models;

import com.eventosapi.demo.enums.StatusInscricao;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;

// domain/entity/Inscricao.java
@Entity
@Table(name = "inscricao",
       uniqueConstraints = @UniqueConstraint(name = "uq_inscricao", columnNames = {"id_evento", "id_usuario"}))
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"evento", "usuario"})
@Builder(toBuilder = true)
public class Inscricao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscricao")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_evento", nullable = false, foreignKey = @ForeignKey(name = "fk_inscricao_evento"))
    private Evento evento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false, foreignKey = @ForeignKey(name = "fk_inscricao_usuario"))
    private Usuario usuario;

    @Builder.Default
    @Column(name = "data", nullable = false)
    private LocalDateTime data = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 255)
    private StatusInscricao status;
}

//@Entity
//@Table(name = "inscricao",
//       uniqueConstraints = { @UniqueConstraint(
//       columnNames = {"id_evento", "id_usuario"})})
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Inscricao {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id_inscricao")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_evento", nullable = false)
//    private Evento evento;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_usuario", nullable = false)
//    private Usuario usuario;
//
//    @Column(name = "data", nullable = false)
//    private LocalDateTime data = LocalDateTime.now();
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "status", length = 255)
//    private StatusInscricao status;
//