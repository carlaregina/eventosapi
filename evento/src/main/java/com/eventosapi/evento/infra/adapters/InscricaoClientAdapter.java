package com.eventosapi.evento.infra.adapters;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.eventosapi.evento.application.port.InscricaoClientPort;
import com.eventosapi.evento.domain.model.Inscricao;
import com.eventosapi.evento.infra.clients.InscricaoFeignClient;
import com.eventosapi.evento.infra.dtos.InscricaoResponseDTO;
import com.eventosapi.evento.interfaces.dto.FiltroInscricaoDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InscricaoClientAdapter implements InscricaoClientPort {

    private final InscricaoFeignClient feignClient;

    @Override
    public List<Inscricao> findAllByEventoId(Long eventId) {
        return this.feignClient.findAll(new FiltroInscricaoDTO(), PageRequest.ofSize(999999))
            .getBody().getContent().stream()
            .map(InscricaoResponseDTO::toDomain)
            .toList();
    }

    @Override
    public Page<Inscricao> findAllByEventoId(Long eventoId, Pageable pageable) {
        FiltroInscricaoDTO filtro = new FiltroInscricaoDTO(eventoId, null, null, null, null);
        return this.feignClient.findAll(filtro, pageable).getBody().map(InscricaoResponseDTO::toDomain);
    }

}
