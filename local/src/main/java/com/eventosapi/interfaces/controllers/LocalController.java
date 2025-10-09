package com.eventosapi.interfaces.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import static org.springframework.http.HttpStatus.CREATED;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventosapi.application.services.LocalService;
import com.eventosapi.domain.models.Local;
import com.eventosapi.interfaces.dtos.FiltroLocalDTO;
import com.eventosapi.interfaces.dtos.LocalRequestDTO;
import com.eventosapi.interfaces.dtos.LocalResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/locais")
@RequiredArgsConstructor
@Tag(name = "Locais")
public class LocalController {  
    private final LocalService localService;
    
    @GetMapping
    @Operation(summary = "Listar locais com paginação e filtros")
    public Page<LocalResponseDTO> listar(FiltroLocalDTO filtro, Pageable pageable) {
        return localService.buscarTodosLocais(filtro, pageable).map(LocalService::toResponseDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter local por ID")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Local encontrado", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Local.class)) }),
        @ApiResponse(responseCode = "404", description = "Local não encontrado", 
            content = @Content)
    })
    public ResponseEntity<LocalResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(LocalService.toResponseDTO(localService.obterLocalPorId(id)));
    }

    @PostMapping
    @Operation(summary = "Criar um novo local")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Local criado", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Local.class)) }),
    })
    public ResponseEntity<LocalResponseDTO> salvar(@Valid @RequestBody LocalRequestDTO dto) {
        LocalResponseDTO localResponseDTO = LocalService.toResponseDTO(localService.cadastrarLocal(LocalService.fromRequestDTO(dto)));
        return ResponseEntity.status(CREATED).body(localResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um local existente")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Local atualizado", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Local.class)) }),
        @ApiResponse(responseCode = "404", description = "Local não encontrado", 
            content = @Content)
    })
    public ResponseEntity<LocalResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody LocalRequestDTO dto) {
        LocalResponseDTO localResponseDTO = LocalService.toResponseDTO(localService.atualizarLocal(id, LocalService.fromRequestDTO(dto)));
        return ResponseEntity.ok(localResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um local por ID")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "Local apagado", 
            content = { @Content(mediaType = "application/json") }),
        @ApiResponse(responseCode = "404", description = "Local não encontrado", 
            content = @Content)
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        localService.deletarLocal(id);
        return ResponseEntity.noContent().build();
    }
}