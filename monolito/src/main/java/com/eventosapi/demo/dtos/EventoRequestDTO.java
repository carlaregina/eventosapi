package com.eventosapi.demo.dtos;

import com.eventosapi.demo.enums.TipoEvento;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoRequestDTO {
    private Long id;

    @NotBlank(message = "O título é obrigatório")
    @Size(max = 255, message = "O título deve ter no máximo 255 caracteres")
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotNull(message = "A data do evento é obrigatória")
    @Future(message = "A data deve estar no futuro")
    private LocalDateTime data;

    @NotNull(message = "O tipo de evento é obrigatório")
    private TipoEvento tipo;

    @NotNull(message = "O número máximo de participantes é obrigatório")
    @Positive(message = "O número de participantes deve ser maior que zero")
    private Integer maxParticipantes;

    @NotNull(message = "O organizador é obrigatório")
    private Long organizadorId;

    @NotNull(message = "O local é obrigatório")
    private Long localId;
}
