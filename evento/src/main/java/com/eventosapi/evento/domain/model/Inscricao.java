package com.eventosapi.evento.domain.model;

import com.eventosapi.evento.domain.enums.StatusInscricao;

import java.time.LocalDateTime;


public class Inscricao {

    private Long id;

    private Long idUsuario;

    private Long idEvento;

    private LocalDateTime data = LocalDateTime.now();

    private StatusInscricao status;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


}