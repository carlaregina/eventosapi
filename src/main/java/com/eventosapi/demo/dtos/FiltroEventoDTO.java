package com.eventosapi.demo.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.eventosapi.demo.enums.TipoEvento;

import lombok.Data;

@Data
public class FiltroEventoDTO {
    private String titulo;
    private String descricao;
    private LocalDateTime data;
    private LocalDateTime dataMaiorQue;
    private LocalDateTime dataMenorQue;
    private List<TipoEvento> tipos;
    private Long organizadorId;
    private Long localId;
}
