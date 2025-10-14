package com.eventosapi.comunicacoes.infra.adapters;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.eventosapi.comunicacoes.application.port.UsuarioClientPort;
import com.eventosapi.comunicacoes.domain.model.Usuario;
import com.eventosapi.comunicacoes.infra.clients.UsuarioFeignClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsuarioClientAdapter implements UsuarioClientPort {

    private final UsuarioFeignClient feignClient;

    @Override
    public List<Usuario> findAll() {
        return feignClient.findAll(PageRequest.ofSize(999999)).getBody().getContent();
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        try {
            return Optional.ofNullable(feignClient.findById(id).getBody());
        } catch (Exception e) {
            log.error("Erro ao buscar inscrição com id {}: {}", id, e.getMessage());
            return Optional.empty();
        }
    }

}
