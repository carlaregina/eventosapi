package com.eventosapi.inscricao.interfaces.dto;

import com.eventosapi.inscricao.domain.enums.StatusInscricao;
import java.time.LocalDateTime;

public record InscricaoResponseDTO(
    Long id,
    Long eventoId,
    Long usuarioId,
    StatusInscricao status,
    LocalDateTime data
) {}
