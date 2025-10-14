package com.eventosapi.evento.infra.dtos;

import java.time.LocalDateTime;

import com.eventosapi.evento.domain.enums.StatusInscricao;
import com.eventosapi.evento.domain.model.Inscricao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscricaoResponseDTO {
    private Long id;
    private Long eventoId;
    private Long usuarioId;
    private StatusInscricao status;
    private LocalDateTime data;

    public Inscricao toDomain() {
        return new Inscricao(id, usuarioId, eventoId, data, status);
    }
}
