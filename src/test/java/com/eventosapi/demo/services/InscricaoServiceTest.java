package com.eventosapi.demo.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.eventosapi.demo.dtos.FiltroIncricaoDTO;
import com.eventosapi.demo.dtos.InscricaoDTO;
import com.eventosapi.demo.dtos.InscricaoResponse;
import com.eventosapi.demo.enums.StatusInscricao;
import com.eventosapi.demo.enums.TipoEvento;
import com.eventosapi.demo.enums.TipoUsuario;
import com.eventosapi.demo.exceptions.EntidadeNaoEncontradoException;
import com.eventosapi.demo.models.Evento;
import com.eventosapi.demo.models.Inscricao;
import com.eventosapi.demo.models.Usuario;
import com.eventosapi.demo.repositories.EventoRepository;
import com.eventosapi.demo.repositories.InscricaoRepository;
import com.eventosapi.demo.repositories.UsuarioRepository;

class InscricaoServiceTest {

    @Mock
    private InscricaoRepository localRepository;
    
    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EventoRepository eventoRepository;

    @InjectMocks
    private InscricaoService localService;
    
    private InscricaoDTO inscricaoDTO;
    private Inscricao inscricao;
    private Usuario usuario;
    private Evento evento;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Usuario Teste");
        usuario.setEmail("teste@email.com");
        usuario.setTipo(TipoUsuario.STAFF);

        evento = new Evento();
        evento.setId(1L);
        evento.setTitulo("Evento Teste");
        evento.setDescricao("Descricao do Evento Teste");
        evento.setOrganizador(usuario);
        evento.setData(LocalDateTime.now());
        evento.setMaxParticipantes(10);
        evento.setTipo(TipoEvento.CURSO);
        
        inscricaoDTO = new InscricaoDTO(1L, 1L, 1L, LocalDateTime.now());

        inscricao = new Inscricao();
        inscricao.setId(1L);
        inscricao.setEvento(evento);
        inscricao.setUsuario(usuario);
        inscricao.setData(inscricaoDTO.data());
        inscricao.setStatus(StatusInscricao.PENDENTE);
    }

    @Test
    void deveListarInscricoes() {
        FiltroIncricaoDTO filtro = new FiltroIncricaoDTO();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Inscricao> page = new PageImpl<>(List.of(inscricao));
        when(localRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

        Page<InscricaoResponse> result = localService.listar(filtro, pageable);

        assertEquals(1, result.getTotalElements());
        verify(localRepository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void deveBuscarInscricaoPorId() {
        when(localRepository.findById(1L)).thenReturn(Optional.of(inscricao));

        Inscricao result = localService.buscar(1L);

        assertNotNull(result);
        assertEquals(inscricao.getEvento(), result.getEvento());
        assertEquals(inscricao.getUsuario(), result.getUsuario());
        assertEquals(inscricao.getStatus(), result.getStatus());
        verify(localRepository).findById(1L);
    }

    @Test
    void deveLancarExceptionAoBuscarInscricaoInexistente() {
        when(localRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradoException.class, () -> localService.buscar(2L));
        verify(localRepository).findById(2L);
    }

    @Test
    void deveSalvarInscricao() {
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(evento));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(localRepository.existsByEventoIdAndUsuarioId(1L, 1L)).thenReturn(false);
        when(localRepository.save(any(Inscricao.class))).thenReturn(inscricao);

        Inscricao result = localService.criar(inscricaoDTO.idEvento(), inscricaoDTO.idUsuario());

        assertNotNull(result);
        assertEquals(inscricaoDTO.idEvento(), result.getEvento().getId());
        assertEquals(inscricaoDTO.idUsuario(), result.getUsuario().getId());
        verify(localRepository).save(any(Inscricao.class));
    }

    @Test
    void deveAtualizarStatusInscricao() {
        Inscricao inscricao = new Inscricao();
        inscricao.setId(1L);
        inscricao.setEvento(evento);
        inscricao.setUsuario(usuario);
        inscricao.setData(LocalDateTime.now());
        inscricao.setStatus(StatusInscricao.PENDENTE);

        when(localRepository.findById(1L)).thenReturn(Optional.of(inscricao));
        when(localRepository.save(inscricao)).thenReturn(inscricao);

        Inscricao result = localService.atualizarStatus(1L, StatusInscricao.CANCELADO);

        assertEquals(result.getStatus(), StatusInscricao.CANCELADO);
        verify(localRepository).save(inscricao);
    }

    @Test
    void deveRemoverInscricaoPorId() {
        when(localRepository.findById(1L)).thenReturn(Optional.of(inscricao));
        doNothing().when(localRepository).delete(inscricao);

        assertDoesNotThrow(() -> localService.excluir(1L));
        verify(localRepository).delete(inscricao);
    }

    @Test
    void removerInscricaoPorIdInexistenteDeveLancarException() {
        when(localRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradoException.class, () -> localService.excluir(2L));
        verify(localRepository).findById(2L);
    }
}