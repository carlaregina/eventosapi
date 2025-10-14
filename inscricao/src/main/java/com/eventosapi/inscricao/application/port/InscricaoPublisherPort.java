package com.eventosapi.inscricao.application.port;

import com.eventosapi.inscricao.interfaces.dto.InscricaoVoucherDTO;

public interface InscricaoPublisherPort {

    void publicarInscricaoCriada(InscricaoVoucherDTO inscricao);

}
