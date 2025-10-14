package com.eventosapi.evento.infra.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.eventosapi.evento.domain.model.Inscricao;
import com.eventosapi.evento.infra.feign.FeignAuthConfig;
import com.eventosapi.evento.interfaces.dto.FiltroInscricaoDTO;

@FeignClient(name = "inscricao-api", url = "${servicos.inscricao.url}", configuration = FeignAuthConfig.class)
public interface InscricaoFeignClient {

    @GetMapping
    ResponseEntity<Page<Inscricao>> findAll(FiltroInscricaoDTO filtro, Pageable page);

}
