package com.eventosapi.comunicacoes.interfaces.dto;

import com.eventosapi.comunicacoes.domain.enums.StatusInscricao;

import java.time.LocalDateTime;

public class InscricaoDTO {
    private Long id;
    private Long idEvento;
    private Long idUsuario;
    private LocalDateTime data;
    private StatusInscricao status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }



    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
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

