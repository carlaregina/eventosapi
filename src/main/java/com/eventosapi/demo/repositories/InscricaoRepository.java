package com.eventosapi.demo.repositories;

import com.eventosapi.demo.models.Inscricao;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;



@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
   
    @EntityGraph(attributePaths = {"evento","usuario"})
    Optional<Inscricao> findById(Long id);

    @EntityGraph(attributePaths = {"evento","usuario"})
    List<Inscricao> findByEventoId(Long idEvento);

    @EntityGraph(attributePaths = {"evento","usuario"})
    List<Inscricao> findAll();
}

