package com.eventosapi.inscricao.repository;

import com.eventosapi.inscricao.domain.Inscricao;
import com.eventosapi.inscricao.domain.StatusInscricao;  
import com.eventosapi.inscricao.dto.FiltroInscricaoDTO;
import org.springframework.data.jpa.domain.Specification;


public final class InscricaoSpecifications {
  private InscricaoSpecifications(){}
  public static Specification<Inscricao> from(FiltroInscricaoDTO f) {
    return Specification
      .where(status(f.status()))
      .and(evento(f.idEvento()))
      .and(usuario(f.idUsuario()));
  }
  private static Specification<Inscricao> status(StatusInscricao s) {
    return (root,q,cb) -> s==null?null:cb.equal(root.get("status"), s);
  }
  private static Specification<Inscricao> evento(Long id) {
    return (root,q,cb) -> id==null?null:cb.equal(root.get("evento").get("id"), id);
  }
  private static Specification<Inscricao> usuario(Long id) {
    return (root,q,cb) -> id==null?null:cb.equal(root.get("usuario").get("id"), id);
  }
}
