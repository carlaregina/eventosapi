package com.eventosapi.comunicacoes.services;


import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.eventosapi.comunicacoes.application.port.EventoClientPort;
import com.eventosapi.comunicacoes.application.port.UsuarioClientPort;
import com.eventosapi.comunicacoes.domain.model.Evento;
import com.eventosapi.comunicacoes.domain.model.Inscricao;
import com.eventosapi.comunicacoes.domain.model.Usuario;
import com.eventosapi.comunicacoes.interfaces.dto.InscricaoDTO;
import org.springframework.stereotype.Service;


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

    private final UsuarioClientPort usuarioClient;
    private final EventoClientPort eventoClient;

//    public byte[] geraRelatorioPDF(Long idInscricao) {
//        Inscricao inscricao = inscricaoRepository.findById(idInscricao)
//                .orElseThrow(() -> new EntidadeNaoEncontradoException("Inscrição não encontrada"));
//        return geraRelatorioPDF(inscricao);
//    }

    public byte[] geraRelatorioPDF(InscricaoDTO inscricao) {
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

    public Map<String, Object> criaParametros(InscricaoDTO inscricao) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("SAUDACAO_USUARIO", saudacaoUsuario(inscricao));
        parametros.put("NUMERO_INSCRICAO", numeroInscricao(inscricao));
        parametros.put("NOME_EVENTO", nomeEvento(inscricao));
        parametros.put("HORARIO_EVENTO", horarioEvento(inscricao));
        parametros.put("LOCAL_EVENTO", localEvento(inscricao));
        return parametros;
    }

    private String saudacaoUsuario(InscricaoDTO inscricao) {
        Usuario usuario = usuarioClient.findById(inscricao.getIdUsuario());
        return "Olá, "+ usuario.getNome() + ", sua inscrição foi confirmada. Observe os detalhes do evento: ";
    }

    private String numeroInscricao(InscricaoDTO inscricao) {
        return "Número da inscrição: " + inscricao.getId();
    }

    private String nomeEvento(InscricaoDTO inscricao) {
        Evento evento = recuperaEvento(inscricao.getIdEvento());
        return "Evento: " + evento.getTitulo();
    }

    private String horarioEvento(InscricaoDTO inscricao) {
        Evento evento = recuperaEvento(inscricao.getIdEvento());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String horaFormatada = evento.getData().format(formatter);
        return "Horário: " + horaFormatada;
    }

    private String localEvento(InscricaoDTO inscricao) {
        Evento evento = recuperaEvento(inscricao.getIdEvento());
        return "Local: " + evento.getTitulo();
    }

    private Evento recuperaEvento(Long eventoId) {
       return eventoClient.findById(eventoId);
    }
}