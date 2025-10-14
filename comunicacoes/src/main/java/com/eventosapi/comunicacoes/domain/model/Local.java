package com.eventosapi.comunicacoes.domain.model;

import com.eventosapi.comunicacoes.domain.enums.Estado;
import com.eventosapi.comunicacoes.domain.enums.TipoLocal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Local {
    private Long id;
    private String nome;
    private String cep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private Estado estado;
    private TipoLocal tipo;
}