package com.eventosapi.evento.infra.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.eventosapi.evento.domain.model.Usuario;
import com.eventosapi.evento.infra.feign.FeignAuthConfig;

@FeignClient(name = "usuarios-api", url = "${servicos.usuarios.url}", configuration = FeignAuthConfig.class)
public interface UsuarioFeignClient {

    @GetMapping("/{id}")
    Usuario findById(@PathVariable("id") Long id);

    @GetMapping
    Page<Usuario> findAll();

}
