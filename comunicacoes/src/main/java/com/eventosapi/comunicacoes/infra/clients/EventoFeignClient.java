package com.eventosapi.comunicacoes.infra.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.eventosapi.comunicacoes.domain.model.Evento;
import com.eventosapi.comunicacoes.infra.feign.FeignAuthConfig;

@FeignClient(name = "evento-api", url = "${api.evento.list-url}", configuration = FeignAuthConfig.class)
public interface EventoFeignClient {

    @GetMapping("/{id}")
    ResponseEntity<Evento> findById(@PathVariable Long id);

}


