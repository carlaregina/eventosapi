package com.eventosapi.demo.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record InscricaoDTO(

        Long id,

        @NotNull(message = "O id do evento é obrigatório")
        Long idEvento,

        @NotNull(message = "O id do usuário é obrigatório")
        Long idUsuario,

        @NotNull(message = "A data é obrigatória")
        LocalDateTime data
) {}
