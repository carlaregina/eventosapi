package com.eventosapi.demo.controller;

import static org.springframework.http.HttpStatus.CREATED;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.eventosapi.demo.dtos.FiltroLocalDTO;
import com.eventosapi.demo.dtos.LocalDTO;
import com.eventosapi.demo.models.Local;
import com.eventosapi.demo.services.LocalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/locais")
@Tag(name = "Locais")
public class LocalController {

    private final LocalService localService;

    @GetMapping
    @Operation(summary = "Listar livros com paginação e filtros")
    public Page<Local> listar(FiltroLocalDTO filtro, Pageable pageable) {
        return localService.listar(filtro, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter livro por ID")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Livro encontrado", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Local.class)) }),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado", 
            content = @Content)
    })
    public ResponseEntity<Local> buscarPorId(@PathVariable Long id) {
        Local local = localService.buscarPorId(id);
        return ResponseEntity.ok(local);
    }

    @PostMapping
    @Operation(summary = "Criar um novo livro")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Livro criado", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Local.class)) }),
    })
    public ResponseEntity<Local> salvar(@Valid @RequestBody LocalDTO dto) {
        Local salvo = localService.salvar(dto);
        return ResponseEntity.status(CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um livro existente")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Livro atualizado", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Local.class)) }),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado", 
            content = @Content)
    })
    public ResponseEntity<Local> atualizar(@PathVariable Long id, @Valid @RequestBody LocalDTO dto) {
        Local local = localService.atualizar(id, dto);
        return ResponseEntity.ok(local);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um livro por ID")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "Livro apagado", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Local.class)) }),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado", 
            content = @Content)
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        localService.removerPorId(id);
        return ResponseEntity.noContent().build();
    }
}
