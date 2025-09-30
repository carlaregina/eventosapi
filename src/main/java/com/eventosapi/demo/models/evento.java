package com.eventosapi.demo.models;

import com.eventosapi.demo.enums.tipoEvento;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "evento")
public class evento implements Serializable {

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
    private tipoEvento tipo;

    @Column(name = "max_participantes", nullable = false)
    private Integer maxParticipantes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizador", nullable = false, foreignKey = @ForeignKey(name = "fk_evento_organizador"))
    private usuario organizador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_local", nullable = false, foreignKey = @ForeignKey(name = "fk_evento_local"))
    private local local;

    // Construtores
    public evento() {
    }

    public evento(String titulo, String descricao, LocalDateTime data, tipoEvento tipo,
                  Integer maxParticipantes, usuario organizador, local local) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.tipo = tipo;
        this.maxParticipantes = maxParticipantes;
        this.organizador = organizador;
        this.local = local;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {

    }
}