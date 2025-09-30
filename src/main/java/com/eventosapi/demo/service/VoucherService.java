package com.eventosapi.demo.service;

import com.eventosapi.demo.domain.dto.InscricaoDTO;
import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class VoucherService {
    String filePath = ("/relatorios/input/Inscricao.jrxml");

    JasperReport jasperReport;

    public JasperPrint geraRelatorio(InscricaoDTO inscricao) {
        try (InputStream jasperTemplate = getClass().getResourceAsStream("/relatorios/input/Inscricao.jrxml")) {
            if (jasperTemplate == null) {
                throw new RuntimeException("Arquivo .jrxml não encontrado em " + filePath);
            }
            jasperReport = JasperCompileManager.compileReport(jasperTemplate);
            return JasperFillManager.fillReport(jasperReport, criaParametros(inscricao), new JREmptyDataSource());
        } catch (JRException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map criaParametros(InscricaoDTO inscricao) {

        Map parametros = new HashMap();
        parametros.put("SAUDACAO_USUARIO", saudacaoUsuario(inscricao));
        parametros.put("NUMERO_INSCRICAO", numeroInscricao(inscricao));
        parametros.put("NOME_EVENTO", nomeEvento(inscricao));
        parametros.put("HORARIO_EVENTO", horarioEvento(inscricao));
        parametros.put("LOCAL_EVENTO", localEvento(inscricao));

        return parametros;
    }

    private String saudacaoUsuario(InscricaoDTO inscricao) {
        return "Olá, Fulano, sua inscrição foi confirmada. Observe os detalhes do evento: ";
    }

    private String numeroInscricao(InscricaoDTO inscricao){
        return "Número da inscrição: 123456";
    }

    private String nomeEvento(InscricaoDTO inscricao){
        return "Evento: O grande Encontro - 25 anos";
    }

    private String horarioEvento(InscricaoDTO inscricaoDTO){
        return "Horário: 19:00";
    }

    private String localEvento(InscricaoDTO inscricaoDTO){
        return "Domus Hall";
    }
}
