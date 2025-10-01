package com.eventosapi.demo.dtos;

import org.hibernate.validator.constraints.Length;
import com.eventosapi.demo.enums.Estado;
import com.eventosapi.demo.enums.TipoLocal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LocalDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Length(max = 255, message = "O nome deve ter no máximo 255 caracteres")
    private String nome;

    @NotBlank(message = "O cep é obrigatório")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "O cep deve estar no formato 00000-000")
    private String cep;

    @NotBlank(message = "O logradouro é obrigatório")
    @Length(max = 255, message = "O logradouro deve ter no máximo 255 caracteres")
    private String logradouro;

    @NotBlank(message = "O numero é obrigatório")
    @Length(max = 255, message = "O numero deve ter no máximo 255 caracteres")
    private String numero;

    @NotBlank(message = "O bairro é obrigatório")
    @Length(max = 255, message = "O bairro deve ter no máximo 255 caracteres")
    private String bairro;

    @NotBlank(message = "A cidade é obrigatório")
    @Length(max = 255, message = "A cidade deve ter no máximo 255 caracteres")
    private String cidade;

    @NotNull(message = "O estado é obrigatório")
    private Estado estado;

    @NotNull(message = "O tipo é obrigatório")
    private TipoLocal tipo;
}