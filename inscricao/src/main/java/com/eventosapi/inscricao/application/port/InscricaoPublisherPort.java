package com.eventosapi.inscricao.application.port;

import com.eventosapi.inscricao.application.dto.InscricaoResponseDTO;

public interface InscricaoPublisherPort {

    void publicarInscricaoCriada(InscricaoResponseDTO inscricao);
    
}
