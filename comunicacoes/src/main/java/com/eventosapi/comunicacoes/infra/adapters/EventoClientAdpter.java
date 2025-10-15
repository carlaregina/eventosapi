package com.eventosapi.comunicacoes.infra.adapters;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.eventosapi.comunicacoes.application.port.EventoClientPort;
import com.eventosapi.comunicacoes.domain.model.Evento;
import com.eventosapi.comunicacoes.infra.clients.EventoFeignClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventoClientAdpter implements EventoClientPort {

    private final EventoFeignClient feignClient;

    @Override
    public Optional<Evento> findById(Long id) {
        try {
            return Optional.ofNullable(feignClient.findById(id).getBody());
        } catch (Exception e) {
            log.error("Erro ao buscar evento com id {}: {}", id, e.getMessage());
            return Optional.empty();
        }
    }
}
