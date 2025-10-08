package com.eventosapi.inscricao.dto;

import com.eventosapi.inscricao.domain.StatusInscricao;

public record InscricaoRequestDTO(

    Long idEvento, 
    Long idUsuario, 
    StatusInscricao status
    
    ) {}
