package com.eventosapi.evento.infra.client;

import com.eventosapi.evento.application.port.LocalClientPort;
import com.eventosapi.evento.domain.model.Local;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "locais-api", url = "${servicos.locais.url}", fallback = LocalClientMock.class)
@Profile("!mock") // só existe quando não estiver no profile mock

public interface LocalClient extends LocalClientPort {

    @GetMapping("/locais/{id}")
    Local findById(@PathVariable Long id);

    @GetMapping("/locais")
    List<Local> findAll();

}

