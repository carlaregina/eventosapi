package com.eventosapi.evento.infra.client;

import com.eventosapi.evento.application.port.UsuarioClientPort;
import com.eventosapi.evento.domain.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.eventosapi.evento.infra.feign.FeignAuthConfig;

import java.util.List;

@FeignClient(name = "usuarios-api", url = "${servicos.usuarios.url}",  fallback = UsuarioClientMock.class,
 configuration = FeignAuthConfig.class)
public interface UsuarioClient extends UsuarioClientPort {


    @GetMapping("/{id}")
    Usuario findById(@PathVariable("id") Long id);

    @GetMapping
    List<Usuario> findAll();

}
