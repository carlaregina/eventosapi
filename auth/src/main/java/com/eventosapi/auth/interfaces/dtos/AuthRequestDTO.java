package com.eventosapi.auth.interfaces.dtos;

import com.eventosapi.auth.domain.models.Conta;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDTO {
    
    @Email(message = "Formato de email inválido")
    @NotBlank(message = "O email é obrigatório")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    private String senha;

    public Conta toDomain() {
        return new Conta(email, senha);
    }
}
