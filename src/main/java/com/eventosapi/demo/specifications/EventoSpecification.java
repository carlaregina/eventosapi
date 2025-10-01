package com.eventosapi.demo.specifications;

import org.springframework.data.jpa.domain.Specification;
import com.eventosapi.demo.enums.TipoEvento;
import com.eventosapi.demo.models.Evento;

public class EventoSpecification {
    public static Specification<Evento> build() {
        return Specification.unrestricted();
    }

    public static Specification<Evento> comTitulo(String titulo) {
        return (root, query, criteriaBuilder) ->
                titulo != null ? criteriaBuilder.like(criteriaBuilder.lower(root.get("titulo")), "%" + titulo.toLowerCase() + "%") : null;
    }

    public static Specification<Evento> comDescricao(String descricao) {
        return (root, query, criteriaBuilder) ->
                descricao != null ? criteriaBuilder.like(criteriaBuilder.lower(root.get("descricao")), "%" + descricao.toLowerCase() + "%") : null;
    }

    public static Specification<Evento> comTipo(TipoEvento tipo) {
        return (root, query, criteriaBuilder) ->
                tipo != null ? criteriaBuilder.equal(root.get("tipo"), tipo) : null;
    }

}