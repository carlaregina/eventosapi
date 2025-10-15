package com.eventosapi.interfaces.dtos;

import com.eventosapi.domain.enums.Estado;
import com.eventosapi.domain.enums.TipoLocal;
import com.eventosapi.domain.models.Local;

public record LocalResponseDTO(
    String nome,
    String cep,
    String logradouro,
    String numero,
    String bairro,
    String cidade,
    Estado estado,
    TipoLocal tipo
) {
    
    public static LocalResponseDTO toResponseDTO(Local local) {
        return new LocalResponseDTO(
            local.getNome(),
            local.getCep(),
            local.getLogradouro(),
            local.getNumero(),
            local.getBairro(),
            local.getCidade(),
            local.getEstado(),
            local.getTipo()
        );
    }
}