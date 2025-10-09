package com.eventosapi.inscricao.interfaces.dto;

import com.eventosapi.inscricao.domain.enums.StatusInscricao;
import jakarta.validation.constraints.NotNull;


public record InscricaoRequestDTO(
    @NotNull Long idEvento,
    @NotNull Long idUsuario,
    StatusInscricao status
) {}
