package com.eventosapi.evento.infra.adapters;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.eventosapi.evento.application.port.UsuarioClientPort;
import com.eventosapi.evento.domain.model.Usuario;
import com.eventosapi.evento.infra.clients.UsuarioFeignClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsuarioClientAdapter implements UsuarioClientPort {

    private final UsuarioFeignClient feignClient;

    @Override
    public Optional<Usuario> findById(Long id) {
        try {
            return Optional.ofNullable(feignClient.findById(id));
        } catch (Exception e) {
            log.error("Erro ao buscar usuário com id {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public List<Usuario> findAll() {
        return feignClient.findAll().getContent();
    }

    @Override
    public Boolean existsById(Long id) {
        return findById(id).isPresent();
    }

}
