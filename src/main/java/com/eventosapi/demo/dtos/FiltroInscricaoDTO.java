package com.eventosapi.demo.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.eventosapi.demo.enums.StatusInscricao;

import lombok.Data;

@Data
public class FiltroInscricaoDTO {
    private Long eventoId;
    private Long usuarioId;
    private LocalDateTime data;
    private LocalDateTime dataMaiorQue;
    private LocalDateTime dataMenorQue;
    private List<StatusInscricao> status;
}
