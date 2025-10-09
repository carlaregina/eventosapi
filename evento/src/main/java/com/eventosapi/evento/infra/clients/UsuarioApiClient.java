package com.eventosapi.evento.infra.clients;

import static java.util.Collections.singletonList;
import static org.springframework.http.HttpMethod.GET;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.eventosapi.evento.infra.dtos.UsuarioDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UsuarioApiClient {

    @Value("${api.usuario.list-url}")
    private String usuarioApiUrl;
    
    private RestTemplate restTemplate = new RestTemplate();

    public Optional<UsuarioDTO> buscarPorEmail(String token, String email) {
        try {
            URI uri = URI.create(usuarioApiUrl + "?email=" + email);
            ResponseEntity<String> response = restTemplate.exchange(uri, GET, getHeaders(token), String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                String json = response.getBody();
                return converterJsonParaUsuario(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private HttpEntity<String> getHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);
        return new HttpEntity<>(headers);
    }

    private Optional<UsuarioDTO> converterJsonParaUsuario(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            PageWrapper<UsuarioDTO> pageWrapper = mapper.readValue(json, new TypeReference<PageWrapper<UsuarioDTO>>() {});
            if (pageWrapper.getContent() != null && pageWrapper.getContent().length > 0) {
                return Optional.of(pageWrapper.getContent()[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    static class PageWrapper<T> {
        private T[] content;

        public T[] getContent() {
            return content;
        }

        public void setContent(T[] content) {
            this.content = content;
        }
    }
}
