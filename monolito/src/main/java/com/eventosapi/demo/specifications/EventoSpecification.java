package com.eventosapi.demo.specifications;

import static org.springframework.util.StringUtils.hasText;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.eventosapi.demo.enums.TipoEvento;
import com.eventosapi.demo.models.Evento;

public class EventoSpecification {

    public static Specification<Evento> build() {
        return Specification.unrestricted();
    }

    public static Specification<Evento> comTitulo(String titulo) {
        return (root, query, criteriaBuilder) ->
                hasText(titulo) ? criteriaBuilder.like(criteriaBuilder.lower(root.get("titulo")), "%" + titulo.toLowerCase() + "%") : null;
    }

    public static Specification<Evento> comDescricao(String descricao) {
        return (root, query, criteriaBuilder) ->
                hasText(descricao) ? criteriaBuilder.like(criteriaBuilder.lower(root.get("descricao")), "%" + descricao.toLowerCase() + "%") : null;
    }

    public static Specification<Evento> comData(LocalDateTime data) {
        return (root, query, criteriaBuilder) ->
                data != null ? criteriaBuilder.equal(root.get("data"), data) : null;
    }

    public static Specification<Evento> comDataMaiorQue(LocalDateTime data) {
        return (root, query, criteriaBuilder) ->
                data != null ? criteriaBuilder.greaterThan(root.get("data"), data) : null;
    }

    public static Specification<Evento> comDataMenorQue(LocalDateTime data) {
        return (root, query, criteriaBuilder) ->
                data != null ? criteriaBuilder.lessThan(root.get("data"), data) : null;
    }

    public static Specification<Evento> comTipos(List<TipoEvento> tipos) {
        return (root, query, criteriaBuilder) ->
                tipos != null && !tipos.isEmpty() ? root.get("tipo").in(tipos) : null;
    }

    public static Specification<Evento> comOrganizadorId(Long organizadorId) {
        return (root, query, criteriaBuilder) ->
                organizadorId != null ? criteriaBuilder.equal(root.get("organizador").get("id"), organizadorId) : null;
    }

    public static Specification<Evento> comLocalId(Long localId) {
        return (root, query, criteriaBuilder) ->
                localId != null ? criteriaBuilder.equal(root.get("local").get("id"), localId) : null;
    }
}
