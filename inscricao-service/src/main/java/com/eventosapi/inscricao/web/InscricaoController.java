package com.eventosapi.inscricao.web;

import com.eventosapi.inscricao.dto.FiltroInscricaoDTO;
import com.eventosapi.inscricao.dto.InscricaoRequestDTO;
import com.eventosapi.inscricao.dto.InscricaoResponseDTO;
import com.eventosapi.inscricao.service.InscricaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/inscricoes")
@RequiredArgsConstructor
@Tag(name = "Inscrições")
public class InscricaoController {

  private final InscricaoService service;

  @PostMapping
  @Operation(summary = "Criar uma nova inscrição")
  public ResponseEntity<InscricaoResponseDTO> criar(@RequestBody @Valid InscricaoRequestDTO req) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(req));
  }

  @GetMapping("/{id}")
  @Operation(summary = "Obter inscrição por ID")
  public InscricaoResponseDTO buscar(@PathVariable Long id) {
    return service.buscar(id);
  }

  @GetMapping
  @Operation(summary = "Listar inscrições com paginação e filtros")
  public ResponseEntity<Page<InscricaoResponseDTO>> listar(
       FiltroInscricaoDTO filtro,
      Pageable pageable) {
    return ResponseEntity.ok(service.listar(filtro, pageable));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Atualizar status da inscrição")
  public InscricaoResponseDTO atualizar(@PathVariable Long id, @RequestBody @Valid InscricaoRequestDTO req) {
    if (req.status() == null) throw new IllegalArgumentException("status é obrigatório para atualização");
    return service.atualizarStatus(id, req.status());
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Excluir inscrição por ID")
  public void excluir(@PathVariable Long id) {
    service.excluir(id);
  }
}
