package com.eventosapi.interfaces.specification;

import org.springframework.data.jpa.domain.Specification;
import com.eventosapi.infra.entities.UsuarioEntity;

import static org.springframework.util.StringUtils.hasText;

public class UsuarioSpecification {

     public static Specification<UsuarioEntity> build() {
        return Specification.unrestricted();
    }

    public static Specification<UsuarioEntity> comNome(String nome) {
        return (root, query, criteriaBuilder) ->
                hasText(nome) ? criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%") : null;
    }

    public static Specification<UsuarioEntity> comEmail(String email) {
        return (root, query, criteriaBuilder) ->
                hasText(email) ? criteriaBuilder.equal(criteriaBuilder.lower(root.get("email")), email.toLowerCase()) : null;
    }

    public static Specification<UsuarioEntity> comTelefone(String telefone) {
        return (root, query, criteriaBuilder) ->
                hasText(telefone) ? criteriaBuilder.equal(root.get("telefone"), telefone) : null;
    }

    public static Specification<UsuarioEntity> comTipo(String tipo) {
        return (root, query, criteriaBuilder) ->
                hasText(tipo) ? criteriaBuilder.equal(root.get("tipo"), tipo) : null;
    }
}