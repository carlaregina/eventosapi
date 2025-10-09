package com.eventosapi.inscricao.interfaces.dto;

import com.eventosapi.inscricao.domain.enums.StatusInscricao;

public record StatusInscricaoDTO(
    StatusInscricao status
) {
}