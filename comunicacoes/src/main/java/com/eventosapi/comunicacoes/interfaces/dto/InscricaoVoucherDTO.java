package com.eventosapi.comunicacoes.interfaces.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InscricaoVoucherDTO {
    private Long id;
    private Long idEvento;
    private Long idUsuario;
    private LocalDateTime data;
    private String status;
}