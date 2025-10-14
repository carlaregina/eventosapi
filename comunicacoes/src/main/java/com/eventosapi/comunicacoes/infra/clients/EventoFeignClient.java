package com.eventosapi.comunicacoes.infra.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.eventosapi.comunicacoes.domain.model.Evento;

@FeignClient(name = "evento-api", url = "${api.evento.list-url}")
public interface EventoFeignClient {

    @GetMapping("/{id}")
    ResponseEntity<Evento> findById(@PathVariable Long id);

}


