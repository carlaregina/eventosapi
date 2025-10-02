package com.eventosapi.demo.services;

import com.eventosapi.demo.exceptions.EntidadeNaoEncontradoException;
import com.eventosapi.demo.models.Evento;
import com.eventosapi.demo.models.Inscricao;
import com.eventosapi.demo.models.Local;
import com.eventosapi.demo.models.Usuario;
import com.eventosapi.demo.repositories.InscricaoRepository;
import com.eventosapi.demo.repositories.UsuarioRepository;
import net.sf.jasperreports.engine.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class VoucherServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private InscricaoRepository inscricaoRepository;

    @InjectMocks
    private VoucherService voucherService;

    private Usuario usuario;
    private Evento evento;
    private Inscricao inscricao;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Tatiana");

        Local local = new Local();
        local.setNome("Auditório Principal");

        evento = new Evento();
        evento.setId(10L);
        evento.setTitulo("Java Meetup");
        evento.setData(LocalDateTime.of(2025, 10, 5, 19, 0));
        evento.setLocal(local);

        inscricao = new Inscricao();
        inscricao.setId(100L);
        inscricao.setUsuario(usuario);
        inscricao.setEvento(evento);
    }

    @Test
    void deveLancarExcecao_QuandoInscricaoNaoEncontrada() {
        when(inscricaoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> voucherService.geraRelatorioPDF(999L))
                .isInstanceOf(EntidadeNaoEncontradoException.class)
                .hasMessage("Inscrição não encontrada");
    }

    @Test
    void deveCriarParametrosCorretamente() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Map<String, Object> params = voucherService.criaParametros(inscricao);

        assertThat(params.get("SAUDACAO_USUARIO"))
                .isEqualTo("Olá, Tatiana, sua inscrição foi confirmada. Observe os detalhes do evento: ");
        assertThat(params.get("NUMERO_INSCRICAO")).isEqualTo("Número da inscrição: 100");
        assertThat(params.get("NOME_EVENTO")).isEqualTo("Evento: Java Meetup");
        assertThat(params.get("HORARIO_EVENTO")).isEqualTo("Horário: 19:00");
        assertThat(params.get("LOCAL_EVENTO")).isEqualTo("Local: Auditório Principal");
    }

    @Test
    void deveGerarPdfComInscricao() throws JRException {
        // Mock Jasper para não precisar do template real
        JasperReport jasperReport = mock(JasperReport.class);
        JasperPrint jasperPrint = mock(JasperPrint.class);

        try (var mockedStaticCompile = mockStatic(JasperCompileManager.class);
             var mockedStaticFill = mockStatic(JasperFillManager.class);
             var mockedStaticExport = mockStatic(JasperExportManager.class)) {

            // Mockando tanto String quanto InputStream
            mockedStaticCompile.when(() -> JasperCompileManager.compileReport(anyString()))
                    .thenReturn(jasperReport);
            mockedStaticCompile.when(() -> JasperCompileManager.compileReport(any(InputStream.class)))
                    .thenReturn(jasperReport);

            // Aceitar qualquer JRDataSource
            mockedStaticFill.when(() ->
                    JasperFillManager.fillReport(any(JasperReport.class), anyMap(), any(JRDataSource.class))
            ).thenReturn(jasperPrint);

            mockedStaticExport.when(() -> JasperExportManager.exportReportToPdf(jasperPrint))
                    .thenReturn(new byte[]{1, 2, 3});

            when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
            when(inscricaoRepository.findById(100L)).thenReturn(Optional.of(inscricao));

            byte[] pdf = voucherService.geraRelatorioPDF(100L);

            assertThat(pdf).isNotNull();
            assertThat(pdf).containsExactly(1, 2, 3);

            mockedStaticCompile.verify(() -> JasperCompileManager.compileReport(any(InputStream.class)), atLeastOnce());
            mockedStaticFill.verify(() -> JasperFillManager.fillReport(eq(jasperReport), anyMap(), any(JRDataSource.class)));
            mockedStaticExport.verify(() -> JasperExportManager.exportReportToPdf(jasperPrint));
        }
    }
}
