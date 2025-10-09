package com.eventosapi.inscricao.infra.specifications;

import com.eventosapi.inscricao.domain.enums.StatusInscricao;
import com.eventosapi.inscricao.infra.entities.InscricaoEntity;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDateTime;

public final class InscricaoSpecs {
  private InscricaoSpecs() {}

  public static Specification<InscricaoEntity> byIdEvento(Long idEvento) {
    return (root, q, cb) -> idEvento == null ? cb.conjunction() : cb.equal(root.get("eventoId"), idEvento);
  }
  public static Specification<InscricaoEntity> byIdUsuario(Long idUsuario) {
    return (root, q, cb) -> idUsuario == null ? cb.conjunction() : cb.equal(root.get("usuarioId"), idUsuario);
  }
  public static Specification<InscricaoEntity> byStatus(StatusInscricao status) {
    return (root, q, cb) -> status == null ? cb.conjunction() : cb.equal(root.get("status"), status);
  }
  public static Specification<InscricaoEntity> byDataIni(LocalDateTime ini) {
    return (root, q, cb) -> ini == null ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get("data"), ini);
  }
  public static Specification<InscricaoEntity> byDataFim(LocalDateTime fim) {
    return (root, q, cb) -> fim == null ? cb.conjunction() : cb.lessThanOrEqualTo(root.get("data"), fim);
  }
}
