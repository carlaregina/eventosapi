package com.eventosapi.evento.infra.client;

import com.eventosapi.evento.application.port.InscricaoClientPort;
import com.eventosapi.evento.domain.enums.TipoUsuario;
import com.eventosapi.evento.domain.model.Inscricao;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(name = "inscricao-api", url = "${servicos.inscricao.url}")
public interface InscricaoClient extends InscricaoClientPort {

    @GetMapping("/inscricao")
    Page<Inscricao> findAll(
            @RequestParam Long eventoId,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String telefone,
            @RequestParam(required = false) TipoUsuario tipo,
            @RequestParam int page,
            @RequestParam int size
    );

    @GetMapping("/inscricao/{id}")
    Inscricao findById(@PathVariable Long id);
}
