package com.eventosapi.demo.models;

import com.eventosapi.demo.enums.TipoEvento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "evento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Long id;

    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data", nullable = false)
    private LocalDateTime data;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 255)
    private TipoEvento tipo;

    @Column(name = "max_participantes", nullable = false)
    private Integer maxParticipantes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizador", nullable = false, foreignKey = @ForeignKey(name = "fk_evento_organizador"))
    private Usuario organizador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_local", nullable = false, foreignKey = @ForeignKey(name = "fk_evento_local"))
    private Local local;

}