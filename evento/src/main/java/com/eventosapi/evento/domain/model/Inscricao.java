package com.eventosapi.evento.domain.model;

import com.eventosapi.evento.domain.enums.StatusInscricao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inscricao {
    private Long id;
    private Long idUsuario;
    private Long idEvento;
    private LocalDateTime data = LocalDateTime.now();
    private StatusInscricao status;
}