package com.eventosapi.inscricao.application.dtos;

import com.eventosapi.inscricao.domain.enums.StatusInscricao;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public record FiltroInscricaoDTO(
    Long eventoId,
    Long usuarioId,
    StatusInscricao status,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim
) {}
