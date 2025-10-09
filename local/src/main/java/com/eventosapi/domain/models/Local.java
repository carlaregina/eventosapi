package com.eventosapi.domain.models;

import com.eventosapi.domain.enums.Estado;
import com.eventosapi.domain.enums.TipoLocal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;    

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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