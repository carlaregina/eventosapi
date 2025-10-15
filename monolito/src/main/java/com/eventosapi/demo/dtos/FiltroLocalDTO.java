package com.eventosapi.demo.dtos;

import com.eventosapi.demo.enums.Estado;
import com.eventosapi.demo.enums.TipoLocal;
import lombok.Data;

@Data
public class FiltroLocalDTO {
    private String nome;
    private String cep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private Estado estado;
    private TipoLocal tipo;
}