package com.eventosapi.demo.dtos;

import com.eventosapi.demo.enums.TipoEvento;
import lombok.Data;

@Data
public class FiltroEventoDTO {

    private String titulo;

    private String descricao;

    private TipoEvento tipo;
    
}