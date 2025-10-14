package com.eventosapi.comunicacoes.infra.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.eventosapi.comunicacoes.domain.model.Inscricao;
import com.eventosapi.comunicacoes.infra.feign.FeignAuthConfig;

@FeignClient(name = "inscricao-api", url = "${api.inscricao.list-url}", configuration = FeignAuthConfig.class)
public interface InscricaoFeignClient {

    @GetMapping
    ResponseEntity<Page<Inscricao>> findAll(Pageable pageable);

    @GetMapping("{/id}")
    ResponseEntity<Inscricao> findById(@PathVariable Long id);
}
