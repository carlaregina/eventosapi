package com.eventosapi.services;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.eventosapi.application.exceptions.EntidadeNaoEncontradoException;
import com.eventosapi.application.port.LocalRepositoryPort;
import com.eventosapi.application.services.LocalService;
import com.eventosapi.domain.enums.Estado;
import com.eventosapi.domain.enums.TipoLocal;
import static com.eventosapi.domain.enums.TipoLocal.RURAL;
import com.eventosapi.domain.models.Local;
import com.eventosapi.interfaces.dtos.FiltroLocalDTO;

class LocalServiceTest {

    @Mock
    private LocalRepositoryPort localRepository;

    @InjectMocks
    private LocalService localService;

    private Local local;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        local = new Local();
        local.setId(1L);
        local.setNome("Local Teste");
        local.setCep("12345-678");
        local.setLogradouro("Rua Teste");
        local.setNumero("10");
        local.setBairro("Centro");
        local.setCidade("Cidade");
        local.setEstado(Estado.PE);
        local.setTipo(TipoLocal.PRAIA);
    }

    @Test
    void deveListarLocais() {
        FiltroLocalDTO filtro = new FiltroLocalDTO("Novo Nome", "99999-999", "Nova Rua", "99", "Novo Bairro", "Nova Cidade", Estado.PB, RURAL);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Local> page = new PageImpl<>(List.of(local));
        when(localRepository.listar(any(FiltroLocalDTO.class), any(Pageable.class))).thenReturn(page);

        Page<Local> result = localService.buscarTodosLocais(filtro, pageable);

        assertEquals(1, result.getTotalElements());
        verify(localRepository).listar(any(FiltroLocalDTO.class), eq(pageable));
    }

    @Test
    void deveBuscarLocalPorId() {
        when(localRepository.buscarPorId(1L)).thenReturn(Optional.of(local));

        Local result = localService.obterLocalPorId(1L);

        assertNotNull(result);
        assertEquals(local.getNome(), result.getNome());
        verify(localRepository).buscarPorId(1L);
    }

    @Test
    void deveLancarExceptionAoBuscarLocalInexistente() {
        when(localRepository.buscarPorId(2L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradoException.class, () -> localService.obterLocalPorId(2L));
        verify(localRepository).buscarPorId(2L);
    }

    @Test
    void deveSalvarLocal() {
        when(localRepository.salvar(any(Local.class))).thenReturn(local);

        Local result = localService.cadastrarLocal(local);

        assertNotNull(result);
        assertEquals(local.getNome(), result.getNome());
        verify(localRepository).salvar(any(Local.class));
    }

    @Test
    void deveAtualizarLocal() {
        when(localRepository.buscarPorId(1L)).thenReturn(Optional.of(local));
        when(localRepository.salvar(local)).thenReturn(local);

        Local novo = new Local(1L,"Novo Nome", "99999-999", "Nova Rua", "99", "Novo Bairro", "Nova Cidade", Estado.PB, RURAL);

        Local result = localService.atualizarLocal(1L, novo);

        assertEquals("Novo Nome", result.getNome());
        assertEquals("99999-999", result.getCep());
        assertEquals("Nova Rua", result.getLogradouro());
        assertEquals("99", result.getNumero());
        assertEquals("Novo Bairro", result.getBairro());
        assertEquals("Nova Cidade", result.getCidade());
        assertEquals(Estado.PB, result.getEstado());
        assertEquals(RURAL, result.getTipo());
        verify(localRepository).salvar(local);
    }

    @Test
    void deveRemoverLocalPorId() {
        when(localRepository.buscarPorId(1L)).thenReturn(Optional.of(local));
        doNothing().when(localRepository).deletar(1L);

        assertDoesNotThrow((Executable) () -> localService.deletarLocal(1L));
        verify(localRepository).deletar(1L);
    }

    @Test
    void removerLocalPorIdInexistenteDeveLancarException() {
        when(localRepository.buscarPorId(2L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradoException.class, () -> localService.deletarLocal(2L));
        verify(localRepository).buscarPorId(2L);
    }
}