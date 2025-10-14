package com.eventosapi.evento.infra.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.eventosapi.evento.application.port.InscricaoClientPort;
import com.eventosapi.evento.domain.enums.TipoUsuario;
import com.eventosapi.evento.domain.model.Inscricao;
import com.eventosapi.evento.infra.feign.FeignAuthConfig;

@FeignClient(name = "inscricao-api", configuration = FeignAuthConfig.class)
public interface InscricaoClient extends InscricaoClientPort {

    @GetMapping
    Page<Inscricao> findAll(
            @RequestParam Long eventoId,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String telefone,
            @RequestParam(required = false) TipoUsuario tipo,
            @RequestParam int page,
            @RequestParam int size
    );

    @GetMapping("{id}")
    Inscricao findById(@PathVariable Long id);

    @GetMapping("/evento/{id}")
    List<Inscricao> findByEventoId(@PathVariable Long id);

}
