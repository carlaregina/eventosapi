package com.eventosapi.interfaces.dtos;

import com.eventosapi.domain.enums.Estado;
import com.eventosapi.domain.enums.TipoLocal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record LocalRequestDTO(
    @NotBlank(message = "O nome é obrigatório")
    @Length(max = 255, message = "O nome deve ter no máximo 255 caracteres")
     String nome,

    @NotBlank(message = "O cep é obrigatório")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "O cep deve estar no formato 00000-000")
     String cep,

    @NotBlank(message = "O logradouro é obrigatório")
    @Length(max = 255, message = "O logradouro deve ter no máximo 255 caracteres")
     String logradouro,

    @NotBlank(message = "O numero é obrigatório")
    @Length(max = 255, message = "O numero deve ter no máximo 255 caracteres")
     String numero,

    @NotBlank(message = "O bairro é obrigatório")
    @Length(max = 255, message = "O bairro deve ter no máximo 255 caracteres")
     String bairro,

    @NotBlank(message = "A cidade é obrigatório")
    @Length(max = 255, message = "A cidade deve ter no máximo 255 caracteres")
     String cidade,

    @NotNull(message = "O estado é obrigatório")
     Estado estado,

    @NotNull(message = "O tipo é obrigatório")
     TipoLocal tipo
) {
}