package com.eventosapi.evento.infra.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.eventosapi.evento.domain.model.Local;
import com.eventosapi.evento.infra.feign.FeignAuthConfig;

@FeignClient(name = "locais-api", configuration = FeignAuthConfig.class)
public interface LocalFeignClient {

    @GetMapping
    Page<Local> findAll();

    @GetMapping("/{id}")
    Local findById(@PathVariable Long id);

}

