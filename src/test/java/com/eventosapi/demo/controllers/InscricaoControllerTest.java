package com.eventosapi.demo.controllers;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import com.eventosapi.demo.controller.InscricaoController;
import com.eventosapi.demo.dtos.InscricaoRequestDTO;
import com.eventosapi.demo.enums.StatusInscricao;
import com.eventosapi.demo.models.Evento;
import com.eventosapi.demo.models.Inscricao;
import com.eventosapi.demo.models.Usuario;
import com.eventosapi.demo.services.InscricaoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = InscricaoController.class)
@AutoConfigureMockMvc
class InscricaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InscricaoService inscricaoService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void deveCriarInscricaoComSucesso() throws Exception {
        // request corrigido na ordem correta do record
        InscricaoRequestDTO request = new InscricaoRequestDTO(
                1L, // idEvento
                2L, // idUsuario
                StatusInscricao.CONFIRMADA
        );

        Evento evento = Evento.builder()
                .id(1L)
                .titulo("Workshop Java")
                .build();

        Usuario usuario = Usuario.builder()
                .id(2L)
                .nome("Carla")
                .build();

        Inscricao inscricao = Inscricao.builder()
                .id(10L)
                .evento(evento)
                .usuario(usuario)
                .data(LocalDateTime.now())
                .status(StatusInscricao.CONFIRMADA)
                .build();

        when(inscricaoService.criar(request)).thenReturn(inscricao);

        mockMvc.perform(post("/api/inscricoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.status").value("CONFIRMADA"))
                .andExpect(jsonPath("$.tituloEvento").value("Workshop Java"))
                .andExpect(jsonPath("$.nomeUsuario").value("Carla"));
    }

    @Test
    void deveBuscarInscricaoPorId() throws Exception {
        Evento evento = Evento.builder()
                .id(1L)
                .titulo("Workshop Java")
                .build();

        Usuario usuario = Usuario.builder()
                .id(2L)
                .nome("Carla")
                .build();

        Inscricao inscricao = Inscricao.builder()
                .id(10L)
                .evento(evento)
                .usuario(usuario)
                .data(LocalDateTime.now())
                .status(StatusInscricao.CONFIRMADA)
                .build();

        when(inscricaoService.buscar(10L)).thenReturn(inscricao);

        mockMvc.perform(get("/api/inscricoes/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.status").value("CONFIRMADA"))
                .andExpect(jsonPath("$.tituloEvento").value("Workshop Java"))
                .andExpect(jsonPath("$.nomeUsuario").value("Carla"));
    }

    @Test
    void deveAtualizarStatusDaInscricao() throws Exception {
        // request corrigido
        InscricaoRequestDTO request = new InscricaoRequestDTO(
                1L, // idEvento
                2L, // idUsuario
                StatusInscricao.CANCELADO
        );

        Evento evento = Evento.builder()
                .id(1L)
                .titulo("Workshop Java")
                .build();

        Usuario usuario = Usuario.builder()
                .id(2L)
                .nome("Carla")
                .build();

        Inscricao inscricao = Inscricao.builder()
                .id(10L)
                .evento(evento)
                .usuario(usuario)
                .data(LocalDateTime.now())
                .status(StatusInscricao.CANCELADO)
                .build();

        when(inscricaoService.atualizarStatus(eq(10L), eq(StatusInscricao.CANCELADO)))
                .thenReturn(inscricao);

        mockMvc.perform(put("/api/inscricoes/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.status").value("CANCELADO"))
                .andExpect(jsonPath("$.tituloEvento").value("Workshop Java"))
                .andExpect(jsonPath("$.nomeUsuario").value("Carla"));
    }

    @Test
    void deveExcluirInscricao() throws Exception {
        doNothing().when(inscricaoService).excluir(10L);

        mockMvc.perform(delete("/api/inscricoes/10"))
                .andExpect(status().isNoContent());
    }
}
