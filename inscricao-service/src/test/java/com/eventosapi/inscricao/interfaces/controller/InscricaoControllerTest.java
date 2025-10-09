package com.eventosapi.inscricao.interfaces.controller;

import com.eventosapi.inscricao.application.services.InscricaoService;
import com.eventosapi.inscricao.domain.enums.StatusInscricao;
import com.eventosapi.inscricao.exception.GlobalExceptionHandler;
import com.eventosapi.inscricao.interfaces.dto.InscricaoRequestDTO;
import com.eventosapi.inscricao.interfaces.dto.InscricaoResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = InscricaoController.class)
@AutoConfigureMockMvc
@Import(GlobalExceptionHandler.class) 
class InscricaoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    InscricaoService service;

    @Test
    void deveCriarInscricaoComSucesso() throws Exception {
        var req = new InscricaoRequestDTO(1L, 2L, StatusInscricao.CONFIRMADA);
        var resp = new InscricaoResponseDTO(
                10L, StatusInscricao.CONFIRMADA, "Workshop Java", "Carla", LocalDateTime.now()
        );

        when(service.criar(eq(req))).thenReturn(resp);

        mockMvc.perform(post("/api/inscricoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.status").value("CONFIRMADA"))
                .andExpect(jsonPath("$.tituloEvento").value("Workshop Java"))
                .andExpect(jsonPath("$.nomeUsuario").value("Carla"));
    }

    @Test
    void deveBuscarInscricaoPorId() throws Exception {
        var resp = new InscricaoResponseDTO(
                10L, StatusInscricao.CONFIRMADA, "Workshop Java", "Carla", LocalDateTime.now()
        );
        when(service.buscar(10L)).thenReturn(resp);

        mockMvc.perform(get("/api/inscricoes/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.status").value("CONFIRMADA"))
                .andExpect(jsonPath("$.tituloEvento").value("Workshop Java"))
                .andExpect(jsonPath("$.nomeUsuario").value("Carla"));
    }

    @Test
    void deveListarPaginado() throws Exception {
        var item = new InscricaoResponseDTO(
                10L, StatusInscricao.CONFIRMADA, "Workshop Java", "Carla", LocalDateTime.now()
        );
        when(service.listar(new com.eventosapi.inscricao.interfaces.dto.FiltroInscricaoDTO(
                null, null, null, null, null, 0, 10
        ))).thenReturn(List.of(item));
      
        when(service.listar(org.mockito.ArgumentMatchers.any())).thenReturn(List.of(item));

        mockMvc.perform(get("/api/inscricoes?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10));
    }

    @Test
    void deveAtualizarStatus() throws Exception {
        var req = new InscricaoRequestDTO(1L, 2L, StatusInscricao.CANCELADO);
        var resp = new InscricaoResponseDTO(
                10L, StatusInscricao.CANCELADO, "Workshop Java", "Carla", LocalDateTime.now()
        );

        when(service.atualizarStatus(10L, StatusInscricao.CANCELADO)).thenReturn(resp);

        mockMvc.perform(put("/api/inscricoes/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELADO"));
    }

    @Test
    void deveExcluir() throws Exception {
        doNothing().when(service).excluir(10L);

        mockMvc.perform(delete("/api/inscricoes/10"))
                .andExpect(status().isNoContent());
    }
}
