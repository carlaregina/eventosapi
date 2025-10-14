package com.eventosapi.evento.infra.clients;

import com.eventosapi.evento.domain.model.Inscricao;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class InscricaoApiClient {

    @Value("${api.inscricao.list-url}")
    private String inscricaoApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public Optional<Inscricao> buscarPorId(String token, Long id) {
        try {
            URI uri = URI.create(inscricaoApiUrl + "/" + id);
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, getHeaders(token), String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                String json = response.getBody();
                ObjectMapper mapper = new ObjectMapper()
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                Inscricao inscricao = mapper.readValue(json, Inscricao.class);
                return Optional.of(inscricao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Inscricao> buscarPorEventoId(String token, Long eventoId) {
        try {
            URI uri = URI.create(inscricaoApiUrl + "/evento/" + eventoId);
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, getHeaders(token), String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                String json = response.getBody();
                ObjectMapper mapper = new ObjectMapper()
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                return mapper.readValue(json, new TypeReference<List<Inscricao>>() {});
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