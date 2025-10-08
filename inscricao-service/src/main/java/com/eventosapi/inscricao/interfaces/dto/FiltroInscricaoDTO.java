package com.eventosapi.inscricao.interfaces.dto;

import com.eventosapi.inscricao.domain.enums.StatusInscricao;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public record FiltroInscricaoDTO(
    Long idEvento,
    Long idUsuario,
    StatusInscricao status,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
    Integer page,
    Integer size
) {}
