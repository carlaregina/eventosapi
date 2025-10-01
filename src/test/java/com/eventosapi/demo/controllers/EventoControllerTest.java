package com.eventosapi.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import com.eventosapi.demo.controller.EventoController;
import com.eventosapi.demo.dtos.EventoRequestDTO;
import com.eventosapi.demo.dtos.EventoResponseDTO;
import com.eventosapi.demo.dtos.FiltroEventoDTO;
import com.eventosapi.demo.dtos.UsuarioResponseDTO;
import com.eventosapi.demo.enums.TipoEvento;
import com.eventosapi.demo.enums.TipoUsuario;
import com.eventosapi.demo.services.EventoService;

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
        FiltroEventoDTO filtro = new FiltroEventoDTO();
        Pageable pageable = PageRequest.of(0, 10);
        Page<EventoResponseDTO> page = new PageImpl<>(List.of(eventoDTO));
        when(eventoService.listar(eq(filtro), eq(pageable))).thenReturn(page);

        ResponseEntity<Page<EventoResponseDTO>> response = eventoController.listarTodos(filtro, pageable);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals("Evento Teste", response.getBody().getContent().get(0).getTitulo());
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

    
    @Test
    void deveListarOrganizadoresComSucesso() {
        // Arrange
        FiltroEventoDTO filtro = new FiltroEventoDTO(); // preencha se necessário
        PageRequest pageable = PageRequest.of(0, 10);

        List<UsuarioResponseDTO> usuarios = List.of(
            new UsuarioResponseDTO("João", "joao@email.com", "123456789", TipoUsuario.OUTROS),
            new UsuarioResponseDTO("Maria", "maria@email.com", "987654321", TipoUsuario.STAFF)
        );
        Page<UsuarioResponseDTO> pagina = new PageImpl<>(usuarios, pageable, usuarios.size());

        Mockito.when(eventoService.listarUsuariosPorEvento(filtro, pageable)).thenReturn(pagina);

        // Act
        Page<UsuarioResponseDTO> resultado = eventoController.listarOrganizadores(filtro, pageable);

        // Assert
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(2, resultado.getTotalElements());
        Assertions.assertEquals("João", resultado.getContent().get(0).nome());
    }

}
