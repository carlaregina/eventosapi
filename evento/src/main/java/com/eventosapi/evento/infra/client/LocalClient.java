package com.eventosapi.evento.infra.client;

import com.eventosapi.evento.application.port.LocalClientPort;
import com.eventosapi.evento.domain.model.Local;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.eventosapi.evento.infra.feign.FeignAuthConfig;

import java.util.List;

@FeignClient(name = "locais-api", url = "${servicos.locais.url}", fallback = LocalClientMock.class,
 configuration = FeignAuthConfig.class)
public interface LocalClient extends LocalClientPort {

    @GetMapping("/{id}")
    Local findById(@PathVariable Long id);

    @GetMapping
    List<Local> findAll();

}

