package com.eventosapi.demo.services;

import com.eventosapi.demo.dtos.InscricaoRequestDTO;
import com.eventosapi.demo.exceptions.RecursoNaoEncontradoException;
import com.eventosapi.demo.models.Evento;
import com.eventosapi.demo.models.Usuario;
import com.eventosapi.demo.repositories.EventoRepository;
import com.eventosapi.demo.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class VoucherService {

    EventoRepository eventoRepository;
    UsuarioRepository usuarioRepository;

    public byte[] geraRelatorioPDF(InscricaoRequestDTO inscricao) {
        try (InputStream jasperTemplate = getClass().getResourceAsStream("/relatorios/input/Inscricao.jrxml")) {
            if (jasperTemplate == null) {
                throw new RuntimeException("Arquivo .jrxml não encontrado");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperTemplate);
            JasperPrint jasperprint = JasperFillManager.fillReport(jasperReport, criaParametros(inscricao), new JREmptyDataSource());
            return JasperExportManager.exportReportToPdf(jasperprint);
        } catch (JRException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Object> criaParametros(InscricaoRequestDTO inscricao) {

        Map parametros = new HashMap();
        parametros.put("SAUDACAO_USUARIO", saudacaoUsuario(inscricao));
        parametros.put("NUMERO_INSCRICAO", numeroInscricao(inscricao));
        parametros.put("NOME_EVENTO", nomeEvento(inscricao));
        parametros.put("HORARIO_EVENTO", horarioEvento(inscricao));
        parametros.put("LOCAL_EVENTO", localEvento(inscricao));

        return parametros;
    }

    private String saudacaoUsuario(InscricaoRequestDTO inscricao) {
        Usuario usuario = usuarioRepository.findById(inscricao.idUsuario()).orElseThrow();
        return "Olá, "+ usuario.getNome() + ", sua inscrição foi confirmada. Observe os detalhes do evento: ";
    }

    private String numeroInscricao(InscricaoRequestDTO inscricao) {
        return "Número da inscrição: " + inscricao.id();
    }

    private String nomeEvento(InscricaoRequestDTO inscricao) {
        return "Evento: " + recuperaEvento(inscricao).getTitulo();
    }

    private String horarioEvento(InscricaoRequestDTO inscricao) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String horaFormatada = recuperaEvento(inscricao).getData().format(formatter);
        return "Horário: " + horaFormatada;

    }

    private String localEvento(InscricaoRequestDTO inscricao) {
        return "Local: " + recuperaEvento(inscricao).getLocal().getNome();
    }

    private Evento recuperaEvento(InscricaoRequestDTO inscricao) {
        return eventoRepository.findById(inscricao.idEvento()).orElseThrow();
    }
}