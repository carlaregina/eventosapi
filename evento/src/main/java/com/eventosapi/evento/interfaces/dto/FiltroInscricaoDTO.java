package com.eventosapi.evento.interfaces.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.eventosapi.evento.domain.enums.StatusInscricao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiltroInscricaoDTO {
    
    private Long eventoId;
    
    private Long usuarioId;

    private StatusInscricao status;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
    private LocalDateTime dataInicio;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
    private LocalDateTime dataFim;
}