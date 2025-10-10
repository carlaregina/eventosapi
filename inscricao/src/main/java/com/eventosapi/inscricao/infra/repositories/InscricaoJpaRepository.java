package com.eventosapi.inscricao.infra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.eventosapi.inscricao.domain.enums.StatusInscricao;
import com.eventosapi.inscricao.infra.entities.InscricaoEntity;

public interface InscricaoJpaRepository extends JpaRepository<InscricaoEntity, Long>, JpaSpecificationExecutor<InscricaoEntity> {
    boolean existsByEventoIdAndUsuarioId(Long idEvento, Long idUsuario);
    long countByEventoIdAndStatus(Long idEvento, StatusInscricao status);
}