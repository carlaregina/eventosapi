package com.eventosapi.inscricao.infra.adapters;

import static java.util.Collections.singletonList;
import static org.springframework.http.HttpMethod.GET;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.eventosapi.inscricao.application.port.EventoClientPort;
import com.eventosapi.inscricao.domain.models.Evento;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class EventoApiClientAdapter implements EventoClientPort {

	@Value("${api.evento.list-url}")
	private String eventoApiUrl;

	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public Optional<Evento> findById(Long id) {
		try {
			String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
			URI uri = URI.create(eventoApiUrl + "/" + id);
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

	private Optional<Evento> converterJsonParaUsuario(String json) {
		try {
			ObjectMapper mapper = new ObjectMapper()
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			Evento evento = mapper.readValue(json, Evento.class);
			return Optional.of(evento);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
}