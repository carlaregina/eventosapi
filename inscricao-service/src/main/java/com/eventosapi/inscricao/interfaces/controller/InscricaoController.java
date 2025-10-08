package com.eventosapi.inscricao.interfaces.controller;

import com.eventosapi.inscricao.application.services.InscricaoService;
import com.eventosapi.inscricao.interfaces.dto.FiltroInscricaoDTO;
import com.eventosapi.inscricao.interfaces.dto.InscricaoRequestDTO;
import com.eventosapi.inscricao.interfaces.dto.InscricaoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
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
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inscricoes")
@RequiredArgsConstructor
@Tag(name = "Inscrições")
public class InscricaoController {

  private final InscricaoService service;

  @PostMapping
  @Operation(summary = "Criar inscrição")
  public ResponseEntity<InscricaoResponseDTO> criar(@RequestBody @Valid InscricaoRequestDTO req) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(req));
  }

  @GetMapping("/{id}")
  public InscricaoResponseDTO buscar(@PathVariable Long id) { return service.buscar(id); }

  @GetMapping
  public ResponseEntity<List<InscricaoResponseDTO>> listar(@ParameterObject FiltroInscricaoDTO f) {
    return ResponseEntity.ok(service.listar(f));
  }

  @PutMapping("/{id}")
  public InscricaoResponseDTO atualizar(@PathVariable Long id, @RequestBody @Valid InscricaoRequestDTO req) {
    if (req.status() == null) throw new IllegalArgumentException("status é obrigatório para atualização");
    return service.atualizarStatus(id, req.status());
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void excluir(@PathVariable Long id) { service.excluir(id); }
}