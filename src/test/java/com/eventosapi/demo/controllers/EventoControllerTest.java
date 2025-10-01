package com.eventosapi.demo.controllers;

import com.eventosapi.demo.controller.EventoController;
import com.eventosapi.demo.dtos.EventoRequestDTO;
import com.eventosapi.demo.dtos.EventoResponseDTO;
import com.eventosapi.demo.enums.TipoEvento;
import com.eventosapi.demo.services.EventoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EventoControllerTest {

    @Mock
    private EventoService eventoService;

    @InjectMocks
    private EventoController eventoController;

    private EventoResponseDTO eventoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        eventoDTO = EventoResponseDTO.builder()
                .titulo("Evento Teste")
                .descricao("Descrição Teste")
                .data(LocalDateTime.now())
                .tipo(TipoEvento.valueOf("CURSO"))
                .maxParticipantes(100)
                .organizadorId(1L)
                .localId(1L)
                .build();
    }

    @Test
    void deveListarTodos() {
        when(eventoService.listarTodos()).thenReturn(List.of(eventoDTO));

        ResponseEntity<List<EventoResponseDTO>> response = eventoController.listarTodos();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Evento Teste", response.getBody().get(0).getTitulo());
    }

    @Test
    void deveBuscarPorId() {
        when(eventoService.buscarPorId(1L)).thenReturn(eventoDTO);

        ResponseEntity<EventoResponseDTO> response = eventoController.buscarPorId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Evento Teste", response.getBody().getTitulo());
    }

    @Test
    void deveCriarEvento() {
        EventoRequestDTO requestDTO = EventoRequestDTO.builder()
                .titulo("Evento Teste")
                .descricao("Descrição Teste")
                .data(LocalDateTime.now())
                .tipo(TipoEvento.valueOf("CURSO"))
                .maxParticipantes(100)
                .organizadorId(1L)
                .localId(1L)
                .build();

        when(eventoService.criar(requestDTO)).thenReturn(eventoDTO);

        ResponseEntity<EventoResponseDTO> response = eventoController.criar(requestDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Evento Teste", response.getBody().getTitulo());
    }

    @Test
    void deveAtualizarEvento() {
        EventoRequestDTO requestDTO = EventoRequestDTO.builder()
                .titulo("Evento Atualizado")
                .descricao("Descrição Atualizada")
                .data(LocalDateTime.now())
                .tipo(TipoEvento.valueOf("CURSO"))
                .maxParticipantes(200)
                .organizadorId(1L)
                .localId(1L)
                .build();

        EventoResponseDTO atualizadoDTO = EventoResponseDTO.builder()
                .titulo("Evento Atualizado")
                .descricao("Descrição Atualizada")
                .data(LocalDateTime.now())
                .tipo(TipoEvento.valueOf("CURSO"))
                .maxParticipantes(200)
                .organizadorId(1L)
                .localId(1L)
                .build();

        when(eventoService.atualizar(1L, requestDTO)).thenReturn(atualizadoDTO);

        ResponseEntity<EventoResponseDTO> response = eventoController.atualizar(1L, requestDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Evento Atualizado", response.getBody().getTitulo());
    }

    @Test
    void deveDeletarEvento() {
        doNothing().when(eventoService).deletar(1L);

        ResponseEntity<Void> response = eventoController.deletar(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(eventoService, times(1)).deletar(1L);
    }
}
