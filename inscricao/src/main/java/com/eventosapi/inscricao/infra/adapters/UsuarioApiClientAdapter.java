package com.eventosapi.inscricao.infra.adapters;

import org.springframework.stereotype.Component;

import com.eventosapi.inscricao.application.port.UsuarioClientPort;
import com.eventosapi.inscricao.infra.clients.UsuarioFeignClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsuarioApiClientAdapter implements UsuarioClientPort {

    private final UsuarioFeignClient feignClient;

	@Override
	public Boolean existsById(Long id) {
        try {
            return feignClient.findById(id) != null;
        } catch (Exception e) {
            log.error("Erro ao buscar usuário com id {}", id, e);
            return false;
        }
	}
    
}
