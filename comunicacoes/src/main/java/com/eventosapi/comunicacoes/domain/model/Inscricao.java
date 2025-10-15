package com.eventosapi.comunicacoes.domain.model;

import java.time.LocalDateTime;

import com.eventosapi.comunicacoes.domain.enums.StatusInscricao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inscricao {
    private Long id;
    private Evento evento;
    private Usuario usuario;
    private LocalDateTime data = LocalDateTime.now();
    private StatusInscricao status;
}