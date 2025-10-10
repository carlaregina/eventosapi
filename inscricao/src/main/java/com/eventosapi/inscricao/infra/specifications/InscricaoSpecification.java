package com.eventosapi.inscricao.infra.specifications;

import static org.springframework.util.StringUtils.hasText;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.eventosapi.inscricao.domain.enums.StatusInscricao;
import com.eventosapi.inscricao.domain.enums.TipoUsuario;
import com.eventosapi.inscricao.infra.entities.InscricaoEntity;

public final class InscricaoSpecification {
  private InscricaoSpecification() {}

  public static Specification<InscricaoEntity> build() {
        return Specification.unrestricted();
    }

    public static Specification<InscricaoEntity> comData(LocalDateTime data) {
        return (root, query, criteriaBuilder) ->
                data != null ? criteriaBuilder.equal(root.get("data"), data) : null;
    }

    public static Specification<InscricaoEntity> comDataMaiorOuIgualQue(LocalDateTime data) {
        return (root, query, criteriaBuilder) ->
                data != null ? criteriaBuilder.greaterThanOrEqualTo(root.get("data"), data) : null;
    }

    public static Specification<InscricaoEntity> comDataMenorOuIgualQue(LocalDateTime data) {
        return (root, query, criteriaBuilder) ->
                data != null ? criteriaBuilder.lessThanOrEqualTo(root.get("data"), data) : null;
    }

    public static Specification<InscricaoEntity> comStatus(List<StatusInscricao> status) {
        return (root, query, criteriaBuilder) ->
                status != null && !status.isEmpty() ? root.get("status").in(status) : null;
    }

    public static Specification<InscricaoEntity> comUsuarioId(Long usuarioId) {
        return (root, query, criteriaBuilder) ->
                usuarioId != null ? criteriaBuilder.equal(root.get("usuario").get("id"), usuarioId) : null;
    }

    public static Specification<InscricaoEntity> comUsuarioNome(String nome) {
        return (root, query, criteriaBuilder) ->
                hasText(nome) ? criteriaBuilder.like(criteriaBuilder.lower(root.get("usuario").get("nome")), "%" + nome.toLowerCase() + "%") : null;
    }

    public static Specification<InscricaoEntity> comUsuarioEmail(String email) {
        return (root, query, criteriaBuilder) ->
                hasText(email) ? criteriaBuilder.like(criteriaBuilder.lower(root.get("usuario").get("email")), "%" + email.toLowerCase() + "%") : null;
    }

    public static Specification<InscricaoEntity> comUsuarioTelefone(String telefone) {
        return (root, query, criteriaBuilder) ->
                hasText(telefone) ? criteriaBuilder.like(criteriaBuilder.lower(root.get("usuario").get("telefone")), "%" + telefone.toLowerCase() + "%") : null;
    }

    public static Specification<InscricaoEntity> comUsuarioTipo(TipoUsuario tipo) {
        return (root, query, criteriaBuilder) ->
                tipo != null ? criteriaBuilder.equal(root.get("usuario").get("tipo"), tipo) : null;
    }

    public static Specification<InscricaoEntity> comEventoId(Long eventoId) {
        return (root, query, criteriaBuilder) ->
                eventoId != null ? criteriaBuilder.equal(root.get("evento").get("id"), eventoId) : null;
    }
}
