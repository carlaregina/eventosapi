package com.eventosapi.demo.models;

import com.eventosapi.demo.enums.TipoEvento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

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

//    // Construtores
//    public Evento() {
//    }
//
//    public Evento(String titulo, String descricao, LocalDateTime data, TipoEvento tipo,
//                  Integer maxParticipantes, Usuario organizador, Local local) {
//        this.titulo = titulo;
//        this.descricao = descricao;
//        this.data = data;
//        this.tipo = tipo;
//        this.maxParticipantes = maxParticipantes;
//        this.organizador = organizador;
//        this.local = local;
//    }
//
//    // Getters e Setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getTitulo() {
//        return titulo;
//    }
//
//    public void setTitulo(String titulo) {
//        this.titulo = titulo;
//    }
//
//    public String getDescricao() {
//        return descricao;
//    }
//
//    public void setDescricao(String descricao) {
//
//    }
}