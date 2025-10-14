package com.eventosapi.inscricao.interfaces.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eventosapi.inscricao.application.dtos.FiltroInscricaoDTO;
import com.eventosapi.inscricao.application.services.InscricaoService;
import com.eventosapi.inscricao.interfaces.dto.InscricaoRequestDTO;
import com.eventosapi.inscricao.interfaces.dto.InscricaoResponseDTO;
import com.eventosapi.inscricao.interfaces.dto.StatusInscricaoDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/inscricoes")
@RequiredArgsConstructor
@Tag(name = "Inscrições")
public class InscricaoController {

	private final InscricaoService service;

	@PostMapping
	@Operation(summary = "Criar inscrição")
	public ResponseEntity<InscricaoResponseDTO> criar(@RequestBody @Valid InscricaoRequestDTO req) {
		log.info("Criando nova inscrição: {}", req);
		var inscricao = service.salvar(req.toDomain());
		return ResponseEntity.status(HttpStatus.CREATED).body(InscricaoResponseDTO.fromDomain(inscricao));
	}

	@GetMapping("/{id}")
	public ResponseEntity<InscricaoResponseDTO> buscar(@PathVariable Long id) {
		log.info("Buscando inscrição com ID: {}", id);
		var inscricao = service.buscarPorId(id);
		return ResponseEntity.ok(InscricaoResponseDTO.fromDomain(inscricao));
	}

	@GetMapping
	public ResponseEntity<Page<InscricaoResponseDTO>> listar(FiltroInscricaoDTO filtro, Pageable page) {
		log.info("Listando inscrições com filtros: {} e paginação: {}", filtro, page);
		return ResponseEntity.ok(service.listar(filtro, page).map(InscricaoResponseDTO::fromDomain));
	}

	@PutMapping("/{id}/status")
	public ResponseEntity<InscricaoResponseDTO> atualizarStatus(@PathVariable Long id,
			@RequestBody StatusInscricaoDTO body) {
		log.info("Atualizando status da inscrição ID: {} para status: {}", id, body.status());
		var inscricao = service.atualizarStatus(id, body.status());
		return ResponseEntity.ok(InscricaoResponseDTO.fromDomain(inscricao));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		log.info("Excluindo inscrição com ID: {}", id);
		service.excluir(id);
		return ResponseEntity.noContent().build();
	}

}