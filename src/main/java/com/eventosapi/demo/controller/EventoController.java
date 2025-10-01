package com.eventosapi.demo.controller;

import com.eventosapi.demo.dtos.EventoRequestDTO;
import com.eventosapi.demo.dtos.EventoResponseDTO;
import com.eventosapi.demo.dtos.FiltroEventoDTO;
import com.eventosapi.demo.dtos.UsuarioResponseDTO;
import com.eventosapi.demo.services.EventoService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoService eventoService;

    @GetMapping
    public ResponseEntity<Page<EventoResponseDTO>> listarTodos(FiltroEventoDTO filtro, Pageable pageable) {
        Page<EventoResponseDTO> eventos = eventoService.listar(filtro, pageable);
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> buscarPorId(@PathVariable Long id) {
        EventoResponseDTO evento = eventoService.buscarPorId(id);
        return ResponseEntity.ok(evento);
    }

    @PostMapping
    public ResponseEntity<EventoResponseDTO> criar(@RequestBody EventoRequestDTO dto) {
        EventoResponseDTO eventoCriado = eventoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoCriado);
    }


    @PutMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> atualizar(@PathVariable Long id,
                                                       @RequestBody EventoRequestDTO dto) {
        EventoResponseDTO eventoAtualizado = eventoService.atualizar(id, dto);
        return ResponseEntity.ok(eventoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        eventoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/participantes")
    @Operation(summary = "Listar organizadores dos eventos com paginação e filtros")
    public Page<UsuarioResponseDTO> listarOrganizadores(FiltroEventoDTO eventoFiltro, Pageable pageable) {
        return eventoService.listarUsuariosPorEvento(eventoFiltro, pageable);
    }
}