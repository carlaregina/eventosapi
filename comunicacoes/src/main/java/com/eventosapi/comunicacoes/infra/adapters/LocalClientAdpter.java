package com.eventosapi.comunicacoes.infra.adapters;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.eventosapi.comunicacoes.application.port.LocalClientPort;
import com.eventosapi.comunicacoes.domain.model.Local;
import com.eventosapi.comunicacoes.infra.clients.LocalFeignClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocalClientAdpter implements LocalClientPort {

    private final LocalFeignClient feignClient;

    @Override
    public Optional<Local> findById(Long id) {
        try {
            return Optional.ofNullable(feignClient.findById(id).getBody());
        } catch (Exception e) {
            log.error("Erro ao buscar evento com id {}: {}", id, e.getMessage());
            return Optional.empty();
        }
    }
}
