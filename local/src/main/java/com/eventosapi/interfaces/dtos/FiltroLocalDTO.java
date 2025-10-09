package com.eventosapi.interfaces.dtos;

import com.eventosapi.domain.enums.Estado;
import com.eventosapi.domain.enums.TipoLocal;

public record FiltroLocalDTO(
    String nome, 
    String cep, 
    String logradouro, 
    String numero, 
    String bairro, 
    String cidade, 
    Estado estado, 
    TipoLocal tipo) {
} 