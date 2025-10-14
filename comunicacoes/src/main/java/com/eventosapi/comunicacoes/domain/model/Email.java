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
}
