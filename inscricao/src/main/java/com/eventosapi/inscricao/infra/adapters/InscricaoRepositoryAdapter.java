package com.eventosapi.inscricao.infra.adapters;

import static com.eventosapi.inscricao.infra.specifications.InscricaoSpecs.byDataFim;
import static com.eventosapi.inscricao.infra.specifications.InscricaoSpecs.byDataIni;
import static com.eventosapi.inscricao.infra.specifications.InscricaoSpecs.byIdEvento;
import static com.eventosapi.inscricao.infra.specifications.InscricaoSpecs.byIdUsuario;
import static com.eventosapi.inscricao.infra.specifications.InscricaoSpecs.byStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.eventosapi.inscricao.application.port.InscricaoRepositoryPort;
import com.eventosapi.inscricao.domain.enums.StatusInscricao;
import com.eventosapi.inscricao.domain.models.Inscricao;
import com.eventosapi.inscricao.infra.entities.InscricaoEntity;
import com.eventosapi.inscricao.infra.repositories.InscricaoJpaRepository;

@Repository
public class InscricaoRepositoryAdapter implements InscricaoRepositoryPort {

  private final InscricaoJpaRepository jpa;

  public InscricaoRepositoryAdapter(InscricaoJpaRepository jpa) {
    this.jpa = jpa;
  }

  @Override
  public List<Inscricao> findByEventoAndStatus(Long eventoId, StatusInscricao status,
                                             Integer page, Integer size) {
  var pageable = PageRequest.of(page == null ? 0 : page, size == null ? 10 : size);

  var pageResult = jpa.findByEventoIdAndStatus(eventoId, status, pageable);
  return pageResult.getContent(); 
  }

  @Override
  public boolean existsByEventoAndUsuario(Long idEvento, Long idUsuario) {
    return jpa.existsByEventoIdAndUsuarioId(idEvento, idUsuario);
  }

  @Override
  public long countConfirmadasByEvento(Long idEvento) {
    return jpa.countByEventoIdAndStatus(idEvento, StatusInscricao.CONFIRMADA);
  }

@Override
  public Inscricao save(Inscricao i) {
    var e = new InscricaoEntity(i.getId(), i.getEventoId(), i.getUsuarioId(), i.getStatus(), i.getData());
    var saved = jpa.save(e);
  
    i.setId(saved.getId());
    return i;
  }

  @Override
  public Optional<Inscricao> findById(Long id) {
    return jpa.findById(id).map(e ->
        new Inscricao(e.getId(), e.getEventoId(), e.getUsuarioId(), e.getStatus(), e.getData())
    );
  }

  @Override
  public List<Inscricao> findAll(Long idEvento,
                                Long idUsuario,
                                StatusInscricao status,
                                LocalDateTime ini,
                                LocalDateTime fim,
                                int page,
                                int size) {

    var spec = Specification.allOf(
        byIdEvento(idEvento),
        byIdUsuario(idUsuario),
        byStatus(status),
        byDataIni(ini),
        byDataFim(fim)
    );

    var pageRes = jpa.findAll(spec, PageRequest.of(page, size));
    return pageRes.getContent()
        .stream()
        .map(e -> new Inscricao(e.getId(), e.getEventoId(), e.getUsuarioId(), e.getStatus(), e.getData()))
        .toList();
  }

  @Override
  public void deleteById(Long id) {
    jpa.deleteById(id);
  }
}
