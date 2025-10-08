package com.eventosapi.inscricao.infra.repositories;

import com.eventosapi.inscricao.domain.enums.StatusInscricao;
import com.eventosapi.inscricao.infra.entities.InscricaoEntity;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface InscricaoJpaRepository extends JpaRepository<InscricaoEntity, Long> {

  boolean existsByEventoIdAndUsuarioId(Long eventoId, Long usuarioId);

//   @Query("""
//       select count(i) from InscricaoEntity i
//       where i.eventoId = :idEvento and i.status = com.eventosapi.inscricao.domain.models.StatusInscricao.CONFIRMADA
//   """)
//   long countConfirmadasByEvento(@Param("idEvento") Long idEvento);

  long countByEventoIdAndStatus(Long eventoId, StatusInscricao status);
  
  @Query("""
     select i from InscricaoEntity i
     where (:idEvento is null or i.eventoId = :idEvento)
       and (:idUsuario is null or i.usuarioId = :idUsuario)
       and (:status is null or i.status = :status)
       and (:ini is null or i.data >= :ini)
       and (:fim is null or i.data <= :fim)
     order by i.id desc
  """)
  List<InscricaoEntity> search(@Param("idEvento") Long idEvento,
                               @Param("idUsuario") Long idUsuario,
                               @Param("status") StatusInscricao status,
                               @Param("ini") java.time.LocalDateTime ini,
                               @Param("fim") java.time.LocalDateTime fim,
                               Pageable pageable);
}