package com.eventosapi.demo.controller;

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

import com.eventosapi.demo.dtos.FiltroInscricaoDTO;
import com.eventosapi.demo.dtos.InscricaoRequestDTO;
import com.eventosapi.demo.dtos.InscricaoResponseDTO;
import com.eventosapi.demo.services.InscricaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inscricoes")
@RequiredArgsConstructor
@Tag(name = "Inscrições")
public class InscricaoController {

    private final InscricaoService service;

    @PostMapping
    @Operation(summary = "Criar uma nova inscrição")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Inscrição criada", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = InscricaoResponseDTO.class)) }),
    })
    public ResponseEntity<InscricaoResponseDTO> criar(@RequestBody @Valid InscricaoRequestDTO req) {
        var i = service.criar(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(InscricaoResponseDTO.from(i));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter inscrição por ID")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Inscrição encontrada", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = InscricaoResponseDTO.class)) }),
        @ApiResponse(responseCode = "404", description = "Inscrição não encontrada", 
            content = @Content)
    })
    public InscricaoResponseDTO buscar(@PathVariable Long id) {
        return InscricaoResponseDTO.from(service.buscar(id));
    }

    @GetMapping
    @Operation(summary = "Listar inscrições com paginação e filtros")
    public ResponseEntity<Page<InscricaoResponseDTO>> listar(FiltroInscricaoDTO filtro, Pageable pageable) {
        Page<InscricaoResponseDTO> page = service.listar(filtro, pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma inscrição existente")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Inscrição atualizada", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = InscricaoResponseDTO.class)) }),
        @ApiResponse(responseCode = "404", description = "Inscrição não encontrada", 
            content = @Content)
    })
    public InscricaoResponseDTO atualizar(@PathVariable Long id, @RequestBody @Valid InscricaoRequestDTO req) {
        if (req.status() == null) throw new IllegalArgumentException("status é obrigatório para atualização");
        return InscricaoResponseDTO.from(service.atualizarStatus(id, req.status()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma inscrição por ID")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "Inscrição apagada", 
            content = { @Content(mediaType = "application/json") }),
        @ApiResponse(responseCode = "404", description = "Inscrição não encontrada", 
            content = @Content)
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}