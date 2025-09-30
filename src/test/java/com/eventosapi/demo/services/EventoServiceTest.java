package com.eventosapi.demo.services;

import com.eventosapi.demo.dtos.EventoRequestDTO;
import com.eventosapi.demo.dtos.EventoResponseDTO;
import com.eventosapi.demo.enums.TipoEvento;
import com.eventosapi.demo.exceptions.EntidadeNaoEncontradoException;
import com.eventosapi.demo.models.Evento;
import com.eventosapi.demo.models.Local;
import com.eventosapi.demo.models.Usuario;
import com.eventosapi.demo.repositories.EventoRepository;
import com.eventosapi.demo.repositories.LocalRepository;
import com.eventosapi.demo.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EventoServiceTest {

    private EventoRepository eventoRepository;
    private UsuarioRepository usuarioRepository;
    private LocalRepository localRepository;
    private EventoService eventoService;

    private Usuario organizador;
    private Local local;
    private Evento evento;
    private EventoRequestDTO eventoRequest;

    @BeforeEach
    void setup() {
        // criar mocks
        eventoRepository = mock(EventoRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        localRepository = mock(LocalRepository.class);

        // criar service
        eventoService = new EventoService(eventoRepository, usuarioRepository, localRepository);

        // dados de teste
        organizador = new Usuario();
        organizador.setId(1L);

        local = new Local();
        local.setId(2L);

        evento = Evento.builder()
                .titulo("Workshop Spring Boot")
                .descricao("Aprendizado prático de APIs")
                .data(LocalDateTime.of(2025, 10, 15, 18, 30))
                .tipo(TipoEvento.CURSO)
                .maxParticipantes(50)
                .organizador(organizador)
                .local(local)
                .build();

        eventoRequest = EventoRequestDTO.builder()
                .titulo("Workshop Spring Boot")
                .descricao("Aprendizado prático de APIs")
                .data(LocalDateTime.of(2025, 10, 15, 18, 30))
                .tipo(TipoEvento.CURSO)
                .maxParticipantes(50)
                .organizadorId(1L)
                .localId(2L)
                .build();
    }

    @Test
    void deveListarTodosEventos() {
        when(eventoRepository.findAll()).thenReturn(List.of(evento));

        List<EventoResponseDTO> resultado = eventoService.listarTodos();

        assertEquals(1, resultado.size());
        assertEquals("Workshop Spring Boot", resultado.get(0).getTitulo());
    }

    @Test
    void deveBuscarEventoPorId() {
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(evento));

        EventoResponseDTO resultado = eventoService.buscarPorId(1L);

        assertEquals("Workshop Spring Boot", resultado.getTitulo());
        assertEquals(50, resultado.getMaxParticipantes());
    }

    @Test
    void deveLancarExcecaoSeEventoNaoExistir() {
        when(eventoRepository.findById(999L)).thenReturn(Optional.empty());

        EntidadeNaoEncontradoException exception = assertThrows(
                EntidadeNaoEncontradoException.class,
                () -> eventoService.buscarPorId(999L)
        );

        assertTrue(exception.getMessage().contains("Evento não encontrado"));
    }

    @Test
    void deveCriarEvento() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(organizador));
        when(localRepository.findById(2L)).thenReturn(Optional.of(local));
        when(eventoRepository.save(any(Evento.class))).thenReturn(evento);

        EventoResponseDTO resultado = eventoService.criar(eventoRequest);

        assertEquals("Workshop Spring Boot", resultado.getTitulo());
        verify(eventoRepository, times(1)).save(any(Evento.class));
    }

    @Test
    void deveAtualizarEvento() {
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(evento));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(organizador));
        when(localRepository.findById(2L)).thenReturn(Optional.of(local));
        when(eventoRepository.save(any(Evento.class))).thenReturn(evento);

        EventoResponseDTO resultado = eventoService.atualizar(1L, eventoRequest);

        assertEquals("Workshop Spring Boot", resultado.getTitulo());
        verify(eventoRepository, times(1)).save(any(Evento.class));
    }

    @Test
    void deveDeletarEvento() {
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(evento));

        eventoService.deletar(1L);

        verify(eventoRepository, times(1)).delete(evento);
    }

}
