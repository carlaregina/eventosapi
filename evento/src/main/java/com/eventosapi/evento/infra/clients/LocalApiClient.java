package com.eventosapi.evento.infra.clients;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.eventosapi.evento.domain.model.Local;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class LocalApiClient {

    @Value("${api.local.list-url}")
    private String localApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public Optional<Local> buscarPorId(String token, Long id) {
        try {
            URI uri = URI.create(localApiUrl + "/" + id);
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, getHeaders(token), String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                String json = response.getBody();
                ObjectMapper mapper = new ObjectMapper()
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                Local local = mapper.readValue(json, Local.class);
                return Optional.of(local);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Local> buscarTodos(String token) {
        try {
            URI uri = URI.create(localApiUrl);
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, getHeaders(token), String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                String json = response.getBody();
                ObjectMapper mapper = new ObjectMapper()
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                return mapper.readValue(json, new TypeReference<List<Local>>() {});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private HttpEntity<String> getHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);
        return new HttpEntity<>(headers);
    }
}
