package com.eventosapi.evento.interfaces.dto;

import com.eventosapi.evento.domain.enums.TipoEvento;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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
