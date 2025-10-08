package com.eventosapi.inscricao.dto;

import com.eventosapi.inscricao.domain.StatusInscricao;
import java.time.LocalDateTime;

public record InscricaoResponseDTO(

    Long id, StatusInscricao status, 
    String tituloEvento, 
    String nomeUsuario, 
    LocalDateTime data) 
    {}
