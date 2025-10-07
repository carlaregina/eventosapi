package com.eventosapi.demo.specifications;

import org.springframework.data.jpa.domain.Specification;
import com.eventosapi.demo.models.Usuario;
import static org.springframework.util.StringUtils.hasText;

public class UsuarioSpecification {

     public static Specification<Usuario> build() {
        return Specification.unrestricted();
    }

    public static Specification<Usuario> comNome(String nome) {
        return (root, query, criteriaBuilder) ->
                hasText(nome) ? criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%") : null;
    }

    public static Specification<Usuario> comEmail(String email) {
        return (root, query, criteriaBuilder) ->
                hasText(email) ? criteriaBuilder.equal(criteriaBuilder.lower(root.get("email")), email.toLowerCase()) : null;
    }

    public static Specification<Usuario> comTelefone(String telefone) {
        return (root, query, criteriaBuilder) ->
                hasText(telefone) ? criteriaBuilder.equal(root.get("telefone"), telefone) : null;
    }

    public static Specification<Usuario> comTipo(String tipo) {
        return (root, query, criteriaBuilder) ->
                hasText(tipo) ? criteriaBuilder.equal(root.get("tipo"), tipo) : null;
    }
}