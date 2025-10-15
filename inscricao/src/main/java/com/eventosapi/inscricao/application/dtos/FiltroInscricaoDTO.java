package com.eventosapi.inscricao.application.dtos;

import com.eventosapi.inscricao.domain.enums.StatusInscricao;
import com.eventosapi.inscricao.domain.enums.TipoUsuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiltroInscricaoDTO {
    
    private Long eventoId;
    private Long usuarioId;
    private String nome;
    private String email;
    private String telefone;
    private TipoUsuario tipoUsuario;
    private StatusInscricao status;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
    private LocalDateTime dataInicio;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
    private LocalDateTime dataFim;
}
