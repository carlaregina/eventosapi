package com.eventosapi.infra.clients;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.eventosapi.infra.dtos.UsuarioDTO;

@Component
public class UsuarioApiClient {

    @Value("${api.usuario.list-url}")
    private String usuarioApiUrl;
    
    private RestTemplate restTemplate = new RestTemplate();

    public Optional<UsuarioDTO> buscarPorEmail(String token, String email) {
        return Optional.empty();
    }
}
