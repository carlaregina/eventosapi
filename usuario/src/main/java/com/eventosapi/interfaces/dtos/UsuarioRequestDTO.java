package com.eventosapi.interfaces.dtos;

import com.eventosapi.domain.enums.TipoUsuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.eventosapi.domain.models.Usuario;

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

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres.")
    String senha,

    @NotNull(message = "O tipo de usuário é obrigatório.")
    TipoUsuario tipo) {

    public Usuario toDomain() {
        return Usuario.builder()
            .nome(this.nome)
            .email(this.email)
            .telefone(this.telefone)
            .senha(this.senha)
            .tipo(this.tipo)
            .build();
    }
}