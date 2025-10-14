package com.eventosapi.comunicacoes.infra.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.eventosapi.comunicacoes.application.port.LocalClientPort;
import com.eventosapi.comunicacoes.domain.model.Local;

@FeignClient(name = "locais-api", url = "${servicos.locais.url}", fallback = LocalClientMock.class)
@Profile("!mock") // só existe quando não estiver no profile mock

public interface LocalClient extends LocalClientPort {

    @GetMapping("/{id}")
    Local findById(@PathVariable Long id);

    @GetMapping
    List<Local> findAll();

}

