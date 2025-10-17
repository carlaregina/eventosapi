package com.eventosapi.inscricao.infra.adapters;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.eventosapi.inscricao.application.port.EventoClientPort;
import com.eventosapi.inscricao.domain.models.Evento;
import com.eventosapi.inscricao.infra.clients.EventoFeignClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventoApiClientAdapter implements EventoClientPort {

	private final EventoFeignClient feignClient;

	@Override
	public Optional<Evento> findById(Long id) {
		try {
            return Optional.ofNullable(feignClient.findById(id));
        } catch (Exception e) {
            log.error("Erro ao buscar evento com id {}", id, e);
            return Optional.empty();
        }
	}
}