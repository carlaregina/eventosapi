package com.eventosapi.comunicacoes.infra.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.eventosapi.comunicacoes.domain.model.Local;

@FeignClient(name = "local-api", url = "${api.local.list-url}")
public interface LocalFeignClient {

    @GetMapping("/{id}")
    ResponseEntity<Local> findById(@PathVariable Long id);

}


