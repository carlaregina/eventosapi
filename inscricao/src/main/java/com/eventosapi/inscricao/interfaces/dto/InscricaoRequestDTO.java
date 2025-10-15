package com.eventosapi.inscricao.interfaces.dto;

import java.time.LocalDateTime;

import com.eventosapi.inscricao.domain.enums.StatusInscricao;
import com.eventosapi.inscricao.domain.models.Inscricao;

import jakarta.validation.constraints.NotNull;


public record InscricaoRequestDTO(
    @NotNull Long idEvento,
    @NotNull Long idUsuario,
    StatusInscricao status
) {
    public Inscricao toDomain() {
        return new Inscricao(null, idEvento, idUsuario, status == null ? StatusInscricao.PENDENTE : status, LocalDateTime.now());
    }
}
