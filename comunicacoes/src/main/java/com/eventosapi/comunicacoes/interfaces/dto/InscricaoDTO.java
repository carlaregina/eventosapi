package com.eventosapi.comunicacoes.interfaces.dto;

import com.eventosapi.comunicacoes.domain.enums.StatusInscricao;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InscricaoDTO {
    private Long id;
    private Long idEvento;
    private Long idUsuario;
    private LocalDateTime data;
    private StatusInscricao status;
}

