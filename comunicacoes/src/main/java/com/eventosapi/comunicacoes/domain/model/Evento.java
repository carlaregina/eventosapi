package com.eventosapi.comunicacoes.domain.model;

import java.time.LocalDateTime;

import com.eventosapi.comunicacoes.domain.enums.TipoEvento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evento {
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDateTime data;
    private TipoEvento tipo;
    private Integer maxParticipantes;
    private Long organizadorId;
    private Local local;
}
