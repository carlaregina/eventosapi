package com.eventosapi.demo.services;

import com.eventosapi.demo.dtos.LocalDTO;
import com.eventosapi.demo.enums.TipoLocal;
import com.eventosapi.demo.exceptions.EntidadeNaoEncontradoException;
import com.eventosapi.demo.models.Local;
import com.eventosapi.demo.repositories.LocalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static com.eventosapi.demo.enums.TipoLocal.RURAL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LocalServiceTest {

    @Mock
    private LocalRepository localRepository;

    @InjectMocks
    private LocalService localService;

    private LocalDTO localDTO;
    private Local local;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        localDTO = new LocalDTO();
        localDTO.setNome("Local Teste");
        localDTO.setCep("12345-678");
        localDTO.setLogradouro("Rua Teste");
        localDTO.setNumero("10");
        localDTO.setBairro("Centro");
        localDTO.setCidade("Cidade");
        localDTO.setEstado("UF");
        localDTO.setTipo(TipoLocal.PRAIA);

        local = new Local();
        local.setId(1L);
        local.setNome(localDTO.getNome());
        local.setCep(localDTO.getCep());
        local.setLogradouro(localDTO.getLogradouro());
        local.setNumero(localDTO.getNumero());
        local.setBairro(localDTO.getBairro());
        local.setCidade(localDTO.getCidade());
        local.setEstado(localDTO.getEstado());
        local.setTipo(localDTO.getTipo());
    }

    @Test
    void deveListarLocais() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Local> page = new PageImpl<>(List.of(local));
        when(localRepository.findAll(pageable)).thenReturn(page);

        Page<Local> result = localService.listar(pageable);

        assertEquals(1, result.getTotalElements());
        verify(localRepository).findAll(pageable);
    }

    @Test
    void deveBuscarLocalPorId() {
        when(localRepository.findById(1L)).thenReturn(Optional.of(local));

        Local result = localService.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(local.getNome(), result.getNome());
        verify(localRepository).findById(1L);
    }

    @Test
    void deveLancarExceptionAoBuscarLocalInexistente() {
        when(localRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradoException.class, () -> localService.buscarPorId(2L));
        verify(localRepository).findById(2L);
    }

    @Test
    void deveSalvarLocal() {
        when(localRepository.save(any(Local.class))).thenReturn(local);

        Local result = localService.salvar(localDTO);

        assertNotNull(result);
        assertEquals(localDTO.getNome(), result.getNome());
        verify(localRepository).save(any(Local.class));
    }

    @Test
    void deveAtualizarLocal() {
        when(localRepository.findById(1L)).thenReturn(Optional.of(local));
        when(localRepository.save(local)).thenReturn(local);

        LocalDTO novoDTO = new LocalDTO();
        novoDTO.setNome("Novo Nome");
        novoDTO.setCep("99999-999");
        novoDTO.setLogradouro("Nova Rua");
        novoDTO.setNumero("99");
        novoDTO.setBairro("Novo Bairro");
        novoDTO.setCidade("Nova Cidade");
        novoDTO.setEstado("NV");
        novoDTO.setTipo(RURAL);

        Local result = localService.atualizar(1L, novoDTO);

        assertEquals("Novo Nome", result.getNome());
        assertEquals("99999-999", result.getCep());
        assertEquals("Nova Rua", result.getLogradouro());
        assertEquals("99", result.getNumero());
        assertEquals("Novo Bairro", result.getBairro());
        assertEquals("Nova Cidade", result.getCidade());
        assertEquals("NV", result.getEstado());
        assertEquals(RURAL, result.getTipo());
        verify(localRepository).save(local);
    }

    @Test
    void deveRemoverLocalPorId() {
        when(localRepository.findById(1L)).thenReturn(Optional.of(local));
        doNothing().when(localRepository).delete(local);

        assertDoesNotThrow(() -> localService.removerPorId(1L));
        verify(localRepository).delete(local);
    }

    @Test
    void removerLocalPorIdInexistenteDeveLancarException() {
        when(localRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradoException.class, () -> localService.removerPorId(2L));
        verify(localRepository).findById(2L);
    }
}