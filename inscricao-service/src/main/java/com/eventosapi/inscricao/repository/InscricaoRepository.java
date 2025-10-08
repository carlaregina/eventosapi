package com.eventosapi.inscricao.repository;

import com.eventosapi.inscricao.domain.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.transaction.annotation.Transactional;



@Repository
public interface InscricaoRepository
    extends JpaRepository<Inscricao, Long>, JpaSpecificationExecutor<Inscricao> {

boolean existsByEvento_IdAndUsuario_Id(Long idEvento, Long idUsuario);

  @EntityGraph(attributePaths = {"evento","usuario"})
  Optional<Inscricao> findById(Long id);

  @EntityGraph(attributePaths = {"evento","usuario"})
  List<Inscricao> findByEvento_Id(Long idEvento);

  @EntityGraph(attributePaths = {"evento","usuario"})
  List<Inscricao> findAll();

  @Override
  @EntityGraph(attributePaths = {"evento","usuario"})
  Page<Inscricao> findAll(Specification<Inscricao> spec, Pageable pageable);

  @Query("""
      select count(i) from Inscricao i
      where i.evento.id = :idEvento and i.status = 'CONFIRMADA'
  """)
  long countConfirmadasByEvento(@Param("idEvento") Long idEvento);
}