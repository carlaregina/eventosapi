package com.eventosapi.inscricao.infra.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.eventosapi.inscricao.domain.enums.StatusInscricao;
import com.eventosapi.inscricao.domain.models.Inscricao;
import com.eventosapi.inscricao.infra.entities.InscricaoEntity;



public interface InscricaoJpaRepository extends JpaRepository<InscricaoEntity, Long>, JpaSpecificationExecutor<InscricaoEntity> {

  boolean existsByEventoIdAndUsuarioId(Long eventoId, Long usuarioId);

  long countByEventoIdAndStatus(Long eventoId, StatusInscricao status);
  
  Page<Inscricao> findByEventoIdAndStatus(Long eventoId, StatusInscricao status, Pageable pageable);
                                            
}