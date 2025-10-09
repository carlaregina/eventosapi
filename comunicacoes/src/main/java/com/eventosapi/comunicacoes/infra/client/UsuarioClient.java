package com.eventosapi.comunicacoes.infra.client;

import com.eventosapi.comunicacoes.application.port.UsuarioClientPort;
import com.eventosapi.comunicacoes.domain.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "usuarios-api", url = "${servicos.usuarios.url}",  fallback = UsuarioClientMock.class)
@Profile("!mock") // só existe quando não estiver no profile mock

public interface UsuarioClient extends UsuarioClientPort {


    @GetMapping("{/id}")
    Usuario findById(@PathVariable Long id);

    @GetMapping
    List<Usuario> findAll();

}
