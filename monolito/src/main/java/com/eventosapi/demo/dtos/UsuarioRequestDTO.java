package com.eventosapi.demo.dtos;

import com.eventosapi.demo.enums.TipoUsuario;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 255, message = "O nome deve ter no máximo 255 caracteres.")
    String nome,

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O e-mail deve ser válido.")
    @Size(max = 255, message = "O e-mail deve ter no máximo 255 caracteres.")
    String email,

    @NotBlank(message = "O telefone é obrigatório.")
    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres.")
    String telefone,

    @NotNull(message = "O tipo de usuário é obrigatório.")
    TipoUsuario tipo
) {}