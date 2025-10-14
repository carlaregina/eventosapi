package com.eventosapi.comunicacoes.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.eventosapi.comunicacoes.application.services.PDFService;
import com.eventosapi.comunicacoes.domain.enums.Estado;
import com.eventosapi.comunicacoes.domain.enums.TipoLocal;
import com.eventosapi.comunicacoes.domain.model.Evento;
import com.eventosapi.comunicacoes.domain.model.Inscricao;
import com.eventosapi.comunicacoes.domain.model.Local;
import com.eventosapi.comunicacoes.domain.model.Usuario;

public class PDFServiceTest {

    @InjectMocks
    private PDFService pdfService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarParametrosCorretamente() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setNome("Tatiana");

        Local local = new Local();
        local.setNome("Evento Teste");
        local.setBairro("Centro");
        local.setCidade("Recife");
        local.setEstado(Estado.PE);
        local.setCep("50000-000");
        local.setLogradouro("Rua do Evento");
        local.setNumero("100");
        local.setTipo(TipoLocal.COMERCIAL);

        Evento evento = new Evento();
        evento.setTitulo("Evento Teste");
        evento.setData(LocalDateTime.of(2025, 10, 9, 15, 30));
        evento.setLocal(local);

        Inscricao inscricao = new Inscricao();
        inscricao.setId(1L);
        inscricao.setUsuario(usuario);
        inscricao.setEvento(evento);

        Map<String, Object> parametros = pdfService.criaParametros(inscricao);

        assertEquals("Olá, Tatiana, sua inscrição foi confirmada. Observe os detalhes do evento: ", parametros.get("SAUDACAO_USUARIO"));
        assertEquals("Número da inscrição: 1", parametros.get("NUMERO_INSCRICAO"));
        assertEquals("Evento: Evento Teste", parametros.get("NOME_EVENTO"));
        assertEquals("Horário: 15:30", parametros.get("HORARIO_EVENTO"));
        assertEquals("Local: Evento Teste\n" +
                     "Rua do Evento, 100\n" +
                     "Centro - Recife - PE\n" +
                     "CEP: 50000-000", parametros.get("LOCAL_EVENTO"));
    }

    @Test
    void deveGerarPDF() {
        Usuario usuario = new Usuario();
        usuario.setNome("Tatiana");

        Local local = new Local();
        local.setNome("Evento Teste");
        local.setBairro("Centro");
        local.setCidade("Recife");
        local.setEstado(Estado.PE);
        local.setCep("50000-000");
        local.setLogradouro("Rua do Evento");
        local.setNumero("100");
        local.setTipo(TipoLocal.COMERCIAL);

        Evento evento = new Evento();
        evento.setTitulo("Evento Teste");
        evento.setData(LocalDateTime.of(2025, 10, 9, 15, 30));
        evento.setLocal(local);

        Inscricao inscricao = new Inscricao();
        inscricao.setId(1L);
        inscricao.setUsuario(usuario);
        inscricao.setEvento(evento);

        assertDoesNotThrow(() -> {
            byte[] pdf = pdfService.geraRelatorioPDF(inscricao);
            assertNotNull(pdf);
        });
    }
}
