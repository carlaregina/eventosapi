package com.eventosapi.interfaces.specifications;

import static org.springframework.util.StringUtils.hasText;
import org.springframework.data.jpa.domain.Specification;
import com.eventosapi.infra.entities.LocalEntity;
import com.eventosapi.domain.enums.Estado;
import com.eventosapi.domain.enums.TipoLocal;

public class LocalSpecification {

    public static Specification<LocalEntity> build() {
        return Specification.unrestricted();
    }

    public static Specification<LocalEntity> comNome(String nome) {
        return (root, query, criteriaBuilder) ->
                hasText(nome) ? criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%") : null;
    }

    public static Specification<LocalEntity> comCep(String cep) {
        return (root, query, criteriaBuilder) ->
                hasText(cep) ? criteriaBuilder.equal(root.get("cep"), cep) : null;
    }

    public static Specification<LocalEntity> comLogradouro(String logradouro) {
        return (root, query, criteriaBuilder) ->
                hasText(logradouro) ? criteriaBuilder.like(criteriaBuilder.lower(root.get("logradouro")), "%" + logradouro.toLowerCase() + "%") : null;
    }

    public static Specification<LocalEntity> comNumero(String numero) {
        return (root, query, criteriaBuilder) ->
                hasText(numero) ? criteriaBuilder.equal(root.get("numero"), numero) : null;
    }

    public static Specification<LocalEntity> comBairro(String bairro) {
        return (root, query, criteriaBuilder) ->
                hasText(bairro) ? criteriaBuilder.like(criteriaBuilder.lower(root.get("bairro")), "%" + bairro.toLowerCase() + "%") : null;
    }

    public static Specification<LocalEntity> comCidade(String cidade) {
        return (root, query, criteriaBuilder) ->
                hasText(cidade) ? criteriaBuilder.like(criteriaBuilder.lower(root.get("cidade")), "%" + cidade.toLowerCase() + "%") : null;
    }

    public static Specification<LocalEntity> comEstado(Estado estado) {
        return (root, query, criteriaBuilder) ->
                estado != null ? criteriaBuilder.equal(root.get("estado"), estado) : null;
    }

    public static Specification<LocalEntity> comTipo(TipoLocal tipo) {
        return (root, query, criteriaBuilder) ->
                tipo != null ? criteriaBuilder.equal(root.get("tipo"), tipo) : null;
    }
}
