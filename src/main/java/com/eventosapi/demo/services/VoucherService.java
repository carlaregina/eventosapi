package com.eventosapi.demo.services;


import com.eventosapi.demo.dtos.InscricaoRequest;
import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class VoucherService {
    String filePath = ("/relatorios/input/Inscricao.jrxml");

    JasperReport jasperReport;

    public JasperPrint geraRelatorio(InscricaoRequest inscricao) {
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

    public Map criaParametros(InscricaoRequest inscricao) {

        Map parametros = new HashMap();
        parametros.put("SAUDACAO_USUARIO", saudacaoUsuario(inscricao));
        parametros.put("NUMERO_INSCRICAO", numeroInscricao(inscricao));
        parametros.put("NOME_EVENTO", nomeEvento(inscricao));
        parametros.put("HORARIO_EVENTO", horarioEvento(inscricao));
        parametros.put("LOCAL_EVENTO", localEvento(inscricao));

        return parametros;
    }

    private String saudacaoUsuario(InscricaoRequest inscricao) {
        return "Olá, Fulano, sua inscrição foi confirmada. Observe os detalhes do evento: ";
    }

    private String numeroInscricao(InscricaoRequest inscricao){
        return "Número da inscrição: 123456";
    }

    private String nomeEvento(InscricaoRequest inscricao){
        return "Evento: O grande Encontro - 25 anos";
    }

    private String horarioEvento(InscricaoRequest inscricaoDTO){
        return "Horário: 19:00";
    }

    private String localEvento(InscricaoRequest inscricaoDTO){
        return "Domus Hall";
    }
}
