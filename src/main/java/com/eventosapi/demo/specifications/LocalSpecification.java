package com.eventosapi.demo.specifications;

import com.eventosapi.demo.enums.Estado;
import com.eventosapi.demo.enums.TipoLocal;
import com.eventosapi.demo.models.Local;

import static org.springframework.util.StringUtils.hasText;

import org.springframework.data.jpa.domain.Specification;

public class LocalSpecification {

    public static Specification<Local> build() {
        return Specification.unrestricted();
    }

    public static Specification<Local> comNome(String nome) {
        return (root, query, criteriaBuilder) ->
                hasText(nome) ? criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%") : null;
    }

    public static Specification<Local> comCep(String cep) {
        return (root, query, criteriaBuilder) ->
                hasText(cep) ? criteriaBuilder.equal(root.get("cep"), cep) : null;
    }

    public static Specification<Local> comLogradouro(String logradouro) {
        return (root, query, criteriaBuilder) ->
                hasText(logradouro) ? criteriaBuilder.like(criteriaBuilder.lower(root.get("logradouro")), "%" + logradouro.toLowerCase() + "%") : null;
    }

    public static Specification<Local> comNumero(String numero) {
        return (root, query, criteriaBuilder) ->
                hasText(numero) ? criteriaBuilder.equal(root.get("numero"), numero) : null;
    }

    public static Specification<Local> comBairro(String bairro) {
        return (root, query, criteriaBuilder) ->
                hasText(bairro) ? criteriaBuilder.like(criteriaBuilder.lower(root.get("bairro")), "%" + bairro.toLowerCase() + "%") : null;
    }

    public static Specification<Local> comCidade(String cidade) {
        return (root, query, criteriaBuilder) ->
                hasText(cidade) ? criteriaBuilder.like(criteriaBuilder.lower(root.get("cidade")), "%" + cidade.toLowerCase() + "%") : null;
    }

    public static Specification<Local> comEstado(Estado estado) {
        return (root, query, criteriaBuilder) ->
                estado != null ? criteriaBuilder.equal(root.get("estado"), estado) : null;
    }

    public static Specification<Local> comTipo(TipoLocal tipo) {
        return (root, query, criteriaBuilder) ->
                tipo != null ? criteriaBuilder.equal(root.get("tipo"), tipo) : null;
    }
}
