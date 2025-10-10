package com.eventosapi.inscricao.interfaces.dto;

import com.eventosapi.inscricao.domain.enums.StatusInscricao;
import com.eventosapi.inscricao.domain.models.Inscricao;

import java.time.LocalDateTime;

public record InscricaoResponseDTO(
    Long id,
    Long eventoId,
    Long usuarioId,
    StatusInscricao status,
    LocalDateTime data
) {
    public static InscricaoResponseDTO fromDomain(Inscricao inscricao) {
        return new InscricaoResponseDTO(
            inscricao.getId(),
            inscricao.getEventoId(),
            inscricao.getUsuarioId(),
            inscricao.getStatus(),
            inscricao.getData()
        );
    }
}
