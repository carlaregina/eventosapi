package com.eventosapi.comunicacoes.infra.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.eventosapi.comunicacoes.application.port.EventoClientPort;
import com.eventosapi.comunicacoes.domain.model.Evento;

@FeignClient(name = "eventos-api", url = "${eventos.locais.url}", fallback = EventoClientMock.class)
@Profile("!mock") // só existe quando não estiver no profile mock
public interface EventoClient extends EventoClientPort {

    @GetMapping("/{id}")
    Evento findById(@PathVariable Long id);


}


