package com.eventosapi.demo.dtos;

import com.eventosapi.demo.enums.StatusInscricao;
import jakarta.validation.constraints.NotNull;


public record InscricaoRequestDTO(

             Long id,
             @NotNull
             Long idEvento,
             @NotNull
             Long idUsuario,
             @NotNull
             StatusInscricao status
         ) {}



