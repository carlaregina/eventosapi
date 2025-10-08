package com.eventosapi.inscricao.interfaces.dto;

import com.eventosapi.inscricao.domain.enums.StatusInscricao;
import java.time.LocalDateTime;

public record InscricaoResponseDTO(
    Long id,
    StatusInscricao status,
    String tituloEvento,
    String nomeUsuario,
    LocalDateTime data
) {}
