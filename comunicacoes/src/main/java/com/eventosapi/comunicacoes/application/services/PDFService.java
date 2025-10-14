package com.eventosapi.comunicacoes.application.services;

import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.eventosapi.comunicacoes.domain.model.Inscricao;

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
public class PDFService {

    public byte[] geraRelatorioPDF(Inscricao inscricao) {
        try (InputStream jasperTemplate = getClass().getResourceAsStream("/relatorios/input/Inscricao.jrxml")) {
            if (jasperTemplate == null) {
                throw new RuntimeException("Arquivo .jrxml não encontrado");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperTemplate);
            JasperPrint jasperprint = JasperFillManager.fillReport(jasperReport, criaParametros(inscricao), new JREmptyDataSource());
            System.out.println("PDF gerado");
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
        return "Olá, "+ inscricao.getUsuario().getNome() + ", sua inscrição foi confirmada. Observe os detalhes do evento: ";
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
        return "Local: " + inscricao.getEvento().getLocal().toFormattedString();
    }
}