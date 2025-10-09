package com.eventosapi.evento.interfaces.dto;

import java.time.LocalDateTime;

import com.eventosapi.evento.domain.enums.StatusInscricao;

public class InscricaoDTO {
    private Long id;
    private Long idEvento;
    private Long idUsuario;
    private StatusInscricao status;
    private LocalDateTime data;

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

    public Long getIdEvento() {
        return idEvento;    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}

