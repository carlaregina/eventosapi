package com.eventosapi.comunicacoes.domain.model;

import com.eventosapi.comunicacoes.domain.enums.StatusInscricao;

import java.time.LocalDateTime;

public class Inscricao {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public StatusInscricao getStatus() {
        return status;
    }

    public void setStatus(StatusInscricao status) {
        this.status = status;
    }

    private Long id;

    private Evento evento;


    private Usuario usuario;


    private LocalDateTime data = LocalDateTime.now();

    private StatusInscricao status;
}