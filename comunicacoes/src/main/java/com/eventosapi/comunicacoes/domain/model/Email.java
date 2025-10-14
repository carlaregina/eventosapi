package com.eventosapi.comunicacoes.domain.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Email {
    private String to;
    private String subject;
    private String body;
    private Map<String, byte[]> attachments;

    public static Email inscricaoRealizada(String to, Map<String, byte[]> attachments) {
        return Email.builder()
            .to(to)
            .subject("Bem-vindo! Sua inscrição foi confirmada")
            .body("Parabéns! Sua inscrição foi realizada com sucesso. Segue em anexo seu voucher de inscrição em PDF.")
            .attachments(attachments)
            .build();
    }

    public static Email eventoAtualizado(String to, Map<String, byte[]> attachments) {
        return Email.builder()
            .to(to)
            .subject("Confira os detalhes do seu evento")
            .body("Segue em anexo seu voucher em PDF. Aproveite o evento!")
            .attachments(attachments)
            .build();
    }
}
