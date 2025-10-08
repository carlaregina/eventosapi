package com.eventosapi.inscricao.dto;

import com.eventosapi.inscricao.domain.StatusInscricao;


public record FiltroInscricaoDTO(

    Long idEvento, 
    Long idUsuario, 
    StatusInscricao status) 
    {}
