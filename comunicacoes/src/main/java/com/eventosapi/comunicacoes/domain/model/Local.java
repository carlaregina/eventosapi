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

    public String toFormattedString() {
        StringBuilder endereco = new StringBuilder();
        
        if (nome != null && !nome.trim().isEmpty()) {
            endereco.append(nome).append("\n");
        }
        
        if (logradouro != null && !logradouro.trim().isEmpty()) {
            endereco.append(logradouro);
            if (numero != null && !numero.trim().isEmpty()) {
                endereco.append(", ").append(numero);
            }
            endereco.append("\n");
        }
        
        if (bairro != null && !bairro.trim().isEmpty()) {
            endereco.append(bairro).append(" - ");
        }
        
        if (cidade != null && !cidade.trim().isEmpty()) {
            endereco.append(cidade);
        }
        
        if (estado != null) {
            endereco.append(" - ").append(estado);
        }
        
        if (cep != null && !cep.trim().isEmpty()) {
            endereco.append("\nCEP: ").append(cep);
        }
        
        return endereco.toString();
    }
}