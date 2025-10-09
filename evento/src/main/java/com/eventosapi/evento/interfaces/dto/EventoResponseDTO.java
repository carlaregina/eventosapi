package com.eventosapi.evento.interfaces.dto;


import com.eventosapi.evento.domain.enums.TipoEvento;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Builder
@Data
public class EventoResponseDTO {
    private String titulo;
    private String descricao;
    private LocalDateTime data;
    private TipoEvento tipo;
    private Integer maxParticipantes;
    private Long organizadorId;
    private Long localId;
}
