package com.eventosapi.comunicacoes.infra.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.eventosapi.comunicacoes.domain.model.Usuario;

@FeignClient(name = "usuarios-api", url = "${api.usuario.list-url}")
public interface UsuarioFeignClient {

    @GetMapping
    ResponseEntity<Page<Usuario>> findAll(Pageable page);

    @GetMapping("/{id}")
    ResponseEntity<Usuario> findById(@PathVariable("id") Long id);
}
