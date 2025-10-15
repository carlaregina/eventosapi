package com.eventosapi.demo.controller;

import com.eventosapi.demo.dtos.EventoRequestDTO;
import com.eventosapi.demo.dtos.EventoResponseDTO;
import com.eventosapi.demo.dtos.FiltroEventoDTO;
import com.eventosapi.demo.dtos.FiltroUsuarioDTO;
import com.eventosapi.demo.dtos.UsuarioResponseDTO;
import com.eventosapi.demo.services.EventoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/eventos")
@Tag(name = "Eventos")
public class EventoController {

    private final EventoService eventoService;

    @GetMapping
    @Operation(summary = "Listar evntos com paginação e filtros")
    public ResponseEntity<Page<EventoResponseDTO>> listarTodos(FiltroEventoDTO filtro, Pageable pageable) {
        Page<EventoResponseDTO> eventos = eventoService.listar(filtro, pageable);
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter evento por ID")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Evento encontrado", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = EventoResponseDTO.class)) }),
        @ApiResponse(responseCode = "404", description = "Evento não encontrado", 
            content = @Content)
    })
    public ResponseEntity<EventoResponseDTO> buscarPorId(@PathVariable Long id) {
        EventoResponseDTO evento = eventoService.buscarPorId(id);
        return ResponseEntity.ok(evento);
    }

    @PostMapping
    @Operation(summary = "Criar um novo evento")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Evento criado", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = EventoResponseDTO.class)) }),
    })
    public ResponseEntity<EventoResponseDTO> criar(@RequestBody EventoRequestDTO dto) {
        EventoResponseDTO eventoCriado = eventoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoCriado);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um evento existente")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Evento atualizado", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = EventoResponseDTO.class)) }),
        @ApiResponse(responseCode = "404", description = "Evento não encontrado", 
            content = @Content)
    })
    public ResponseEntity<EventoResponseDTO> atualizar(@PathVariable Long id,
                                                       @RequestBody EventoRequestDTO dto) {
        EventoResponseDTO eventoAtualizado = eventoService.atualizar(id, dto);
        return ResponseEntity.ok(eventoAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um evento por ID")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "Evento apagado", 
            content = { @Content(mediaType = "application/json") }),
        @ApiResponse(responseCode = "404", description = "Evento não encontrado", 
            content = @Content)
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        eventoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/participantes")
    @Operation(summary = "Listar participantes do evento com paginação e filtros")
    public Page<UsuarioResponseDTO> listarOrganizadores(@PathVariable Long id, FiltroUsuarioDTO eventoFiltro, Pageable pageable) {
        return eventoService.listarUsuariosPorEvento(id, eventoFiltro, pageable);
    }
}