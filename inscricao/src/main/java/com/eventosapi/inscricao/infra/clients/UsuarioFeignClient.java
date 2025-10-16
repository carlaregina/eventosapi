package com.eventosapi.inscricao.infra.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.eventosapi.inscricao.domain.models.Usuario;
import com.eventosapi.inscricao.infra.feign.FeignAuthConfig;

@FeignClient(name = "usuarios-api", url = "${api.usuario.list-url}", configuration = FeignAuthConfig.class)
public interface UsuarioFeignClient {

    @GetMapping("/{id}")
    Usuario findById(@PathVariable("id") Long id);

}
