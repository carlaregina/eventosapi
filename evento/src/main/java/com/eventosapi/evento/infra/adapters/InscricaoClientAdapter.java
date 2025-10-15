package com.eventosapi.evento.infra.adapters;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.eventosapi.evento.application.dtos.FiltroInscricaoDTO;
import com.eventosapi.evento.application.port.InscricaoClientPort;
import com.eventosapi.evento.domain.model.Inscricao;
import com.eventosapi.evento.infra.clients.InscricaoFeignClient;
import com.eventosapi.evento.infra.dtos.InscricaoResponseDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InscricaoClientAdapter implements InscricaoClientPort {

    private final InscricaoFeignClient feignClient;

    @Override
    public List<Inscricao> findAllByEventoId(FiltroInscricaoDTO filtro) {
        return this.feignClient.findAll(filtro, PageRequest.ofSize(999999))
            .getBody().getContent().stream()
            .map(InscricaoResponseDTO::toDomain)
            .toList();
    }

    @Override
    public Page<Inscricao> findAllByEventoId(FiltroInscricaoDTO filtro, Pageable pageable) {
        return this.feignClient.findAll(filtro, pageable).getBody().map(InscricaoResponseDTO::toDomain);
    }

}
