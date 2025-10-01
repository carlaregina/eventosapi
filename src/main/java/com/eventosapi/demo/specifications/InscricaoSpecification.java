package com.eventosapi.demo.specifications;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.eventosapi.demo.enums.StatusInscricao;
import com.eventosapi.demo.models.Inscricao;

public class InscricaoSpecification {

    public static Specification<Inscricao> build() {
        return Specification.unrestricted();
    }

    public static Specification<Inscricao> comData(LocalDateTime data) {
        return (root, query, criteriaBuilder) ->
                data != null ? criteriaBuilder.equal(root.get("data"), data) : null;
    }

    public static Specification<Inscricao> comDataMaiorQue(LocalDateTime data) {
        return (root, query, criteriaBuilder) ->
                data != null ? criteriaBuilder.greaterThan(root.get("data"), data) : null;
    }

    public static Specification<Inscricao> comDataMenorQue(LocalDateTime data) {
        return (root, query, criteriaBuilder) ->
                data != null ? criteriaBuilder.lessThan(root.get("data"), data) : null;
    }

    public static Specification<Inscricao> comStatus(List<StatusInscricao> status) {
        return (root, query, criteriaBuilder) ->
                status != null && !status.isEmpty() ? root.get("status").in(status) : null;
    }

    public static Specification<Inscricao> comUsuarioId(Long usuarioId) {
        return (root, query, criteriaBuilder) ->
                usuarioId != null ? criteriaBuilder.equal(root.get("usuario").get("id"), usuarioId) : null;
    }

    public static Specification<Inscricao> comEventoId(Long eventoId) {
        return (root, query, criteriaBuilder) ->
                eventoId != null ? criteriaBuilder.equal(root.get("evento").get("id"), eventoId) : null;
    }
}
