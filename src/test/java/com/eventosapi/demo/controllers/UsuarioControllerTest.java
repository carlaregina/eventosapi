package com.eventosapi.demo.controllers;

import com.eventosapi.demo.controller.UsuarioController;
import com.eventosapi.demo.dtos.UsuarioRequestDTO;
import com.eventosapi.demo.dtos.UsuarioResponseDTO;
import com.eventosapi.demo.enums.TipoUsuario;
import com.eventosapi.demo.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private UsuarioResponseDTO usuarioResponseDTO;
    private UsuarioRequestDTO usuarioRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuarioResponseDTO = new UsuarioResponseDTO(
                "José Vitor",
                "jose.vitor@example.com",
                "11999999999",
                TipoUsuario.STAFF
        );

        usuarioRequestDTO = new UsuarioRequestDTO(
                "José Vitor",
                "jose.vitor@example.com",
                "11999999999",
                TipoUsuario.STAFF
        );
    }

    @Test
    void deveListarTodosUsuarios() {
        when(usuarioService.listarUsuarios()).thenReturn(List.of(usuarioResponseDTO));

        ResponseEntity<List<UsuarioResponseDTO>> response = usuarioController.listarTodos();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("José Vitor", response.getBody().get(0).nome());
    }

    @Test
    void deveObterUsuarioPorId() {
        when(usuarioService.obterUsuarioPorId(1L)).thenReturn(usuarioResponseDTO);

        ResponseEntity<UsuarioResponseDTO> response = usuarioController.obterPorId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("José Vitor", response.getBody().nome());
    }

    @Test
    void deveCadastrarUsuario() {
        when(usuarioService.cadastrarUsuario(usuarioRequestDTO)).thenReturn(usuarioResponseDTO);

        ResponseEntity<UsuarioResponseDTO> response = usuarioController.cadastrar(usuarioRequestDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("José Vitor", response.getBody().nome());
    }

    @Test
    void deveAtualizarUsuario() {
        when(usuarioService.atualizarUsuario(1L, usuarioRequestDTO)).thenReturn(usuarioResponseDTO);

        ResponseEntity<UsuarioResponseDTO> response = usuarioController.atualizar(1L, usuarioRequestDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("José Vitor", response.getBody().nome());
    }

    @Test
    void deveDeletarUsuario() {
        doNothing().when(usuarioService).deletarUsuario(1L);

        ResponseEntity<Void> response = usuarioController.deletar(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(usuarioService, times(1)).deletarUsuario(1L);
    }
}