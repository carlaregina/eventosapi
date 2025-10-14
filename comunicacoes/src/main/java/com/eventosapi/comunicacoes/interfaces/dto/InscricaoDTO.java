package com.eventosapi.comunicacoes.interfaces.dto;

import java.time.LocalDateTime;

import com.eventosapi.comunicacoes.domain.enums.StatusInscricao;

import lombok.Data;

@Data
public class InscricaoDTO {
    private Long id;
    private Long idEvento;
    private Long idUsuario;
    private LocalDateTime data;
    private StatusInscricao status;
}

