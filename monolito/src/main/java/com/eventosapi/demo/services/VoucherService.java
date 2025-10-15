package com.eventosapi.demo.services;

import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.eventosapi.demo.exceptions.EntidadeNaoEncontradoException;
import com.eventosapi.demo.models.Evento;
import com.eventosapi.demo.models.Inscricao;
import com.eventosapi.demo.models.Usuario;
import com.eventosapi.demo.repositories.EventoRepository;
import com.eventosapi.demo.repositories.InscricaoRepository;
import com.eventosapi.demo.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
@RequiredArgsConstructor
public class VoucherService {

    private final UsuarioRepository usuarioRepository;
    private final InscricaoRepository inscricaoRepository;

    public byte[] geraRelatorioPDF(Long idInscricao) {
        Inscricao inscricao = inscricaoRepository.findById(idInscricao)
            .orElseThrow(() -> new EntidadeNaoEncontradoException("Inscrição não encontrada"));
        return geraRelatorioPDF(inscricao);
    }

    public byte[] geraRelatorioPDF(Inscricao inscricao) {
        try (InputStream jasperTemplate = getClass().getResourceAsStream("/relatorios/input/Inscricao.jrxml")) {
            if (jasperTemplate == null) {
                throw new RuntimeException("Arquivo .jrxml não encontrado");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperTemplate);
            JasperPrint jasperprint = JasperFillManager.fillReport(jasperReport, criaParametros(inscricao), new JREmptyDataSource());
            return JasperExportManager.exportReportToPdf(jasperprint);
        } catch (JRException | IOException e) {
            throw new RuntimeException("Erro ao gerar voucher", e);
        }
    }

    public Map<String, Object> criaParametros(Inscricao inscricao) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("SAUDACAO_USUARIO", saudacaoUsuario(inscricao));
        parametros.put("NUMERO_INSCRICAO", numeroInscricao(inscricao));
        parametros.put("NOME_EVENTO", nomeEvento(inscricao));
        parametros.put("HORARIO_EVENTO", horarioEvento(inscricao));
        parametros.put("LOCAL_EVENTO", localEvento(inscricao));
        return parametros;
    }

    private String saudacaoUsuario(Inscricao inscricao) {
        Usuario usuario = usuarioRepository.findById(inscricao.getUsuario().getId()).orElseThrow();
        return "Olá, "+ usuario.getNome() + ", sua inscrição foi confirmada. Observe os detalhes do evento: ";
    }

    private String numeroInscricao(Inscricao inscricao) {
        return "Número da inscrição: " + inscricao.getId();
    }

    private String nomeEvento(Inscricao inscricao) {
        return "Evento: " + inscricao.getEvento().getTitulo();
    }

    private String horarioEvento(Inscricao inscricao) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String horaFormatada = inscricao.getEvento().getData().format(formatter);
        return "Horário: " + horaFormatada;
    }

    private String localEvento(Inscricao inscricao) {
        return "Local: " + inscricao.getEvento().getLocal().getNome();
    }
}