package com.eventosapi.evento.infra.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.eventosapi.evento.infra.dtos.InscricaoResponseDTO;
import com.eventosapi.evento.infra.feign.FeignAuthConfig;
import com.eventosapi.evento.interfaces.dto.FiltroInscricaoDTO;

@FeignClient(name = "inscricao-api", url = "${servicos.inscricao.url}", configuration = FeignAuthConfig.class)
public interface InscricaoFeignClient {

    @GetMapping
    ResponseEntity<Page<InscricaoResponseDTO>> findAll(@SpringQueryMap FiltroInscricaoDTO filtro, Pageable page);

}
