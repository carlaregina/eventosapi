package com.eventosapi.evento.infra.adapters;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.eventosapi.evento.application.port.LocalClientPort;
import com.eventosapi.evento.domain.model.Local;
import com.eventosapi.evento.infra.clients.LocalFeignClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocalClientAdapter implements LocalClientPort {

    private final LocalFeignClient feignClient;

    @Override
    public List<Local> findAll() {
        return feignClient.findAll().getContent();
    }

    @Override
    public Boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public Optional<Local> findById(Long id) {
        try {
            return Optional.ofNullable(feignClient.findById(id));
        } catch (Exception e) {
            log.error("Erro ao buscar local com id {}", id, e);
            return Optional.empty();
        }
    }

}

