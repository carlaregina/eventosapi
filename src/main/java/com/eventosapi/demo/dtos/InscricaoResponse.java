package com.eventosapi.demo.dtos;

import com.eventosapi.demo.enums.StatusInscricao;
import com.eventosapi.demo.models.Inscricao;
import java.time.LocalDateTime;

// api/dto/response/InscricaoResponse.java
public record InscricaoResponse(
    Long id,
    Long idEvento,
    String tituloEvento,
    Long idUsuario,
    String nomeUsuario,
    LocalDateTime data,
    StatusInscricao status
) {
    public static InscricaoResponse from(Inscricao i) {
        var e = i.getEvento();
        var u = i.getUsuario();
        return new InscricaoResponse(
            i.getId(),
            e != null ? e.getId() : null,
            e != null ? e.getTitulo() : null,
            u != null ? u.getId() : null,
            u != null ? u.getNome() : null,
            i.getData(),
            i.getStatus()
        );
    }
}
