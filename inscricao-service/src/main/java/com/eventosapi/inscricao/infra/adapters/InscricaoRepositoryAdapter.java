package com.eventosapi.inscricao.infra.adapters;

import com.eventosapi.inscricao.application.port.InscricaoRepositoryPort;
import com.eventosapi.inscricao.domain.enums.StatusInscricao;
import com.eventosapi.inscricao.domain.models.Inscricao;
import com.eventosapi.inscricao.infra.entities.InscricaoEntity;
import com.eventosapi.inscricao.infra.repositories.InscricaoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;


import static com.eventosapi.inscricao.infra.specifications.InscricaoSpecs.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class InscricaoRepositoryAdapter implements InscricaoRepositoryPort {

  private final InscricaoJpaRepository jpa;

  public InscricaoRepositoryAdapter(InscricaoJpaRepository jpa) {
    this.jpa = jpa;
  }

  @Override
  public boolean existsByEventoAndUsuario(Long idEvento, Long idUsuario) {
    return jpa.existsByEventoIdAndUsuarioId(idEvento, idUsuario);
  }

//   @Override
//   public long countConfirmadasByEvento(Long idEvento) {
//     return jpa.countConfirmadasByEvento(idEvento);
//   }

  @Override
  public long countConfirmadasByEvento(Long idEvento) {
    return jpa.countByEventoIdAndStatus(idEvento, StatusInscricao.CONFIRMADA);
  }

@Override
  public Inscricao save(Inscricao i) {
    var e = new InscricaoEntity(i.getId(), i.getEventoId(), i.getUsuarioId(), i.getStatus(), i.getData());
    var saved = jpa.save(e);
    // Pode devolver um novo domínio com o id salvo;
    // se preferir mutar, lembre-se que seu domínio tem setId():
    i.setId(saved.getId());
    return i;
  }

  @Override
  public Optional<Inscricao> findById(Long id) {
    return jpa.findById(id).map(e ->
        new Inscricao(e.getId(), e.getEventoId(), e.getUsuarioId(), e.getStatus(), e.getData())
    );
  }

//   @Override
//   public List<Inscricao> findAll(Long idEvento,
//                                  Long idUsuario,
//                                  StatusInscricao status,
//                                  LocalDateTime ini,
//                                  LocalDateTime fim,
//                                  int page,
//                                  int size) {
//     return jpa.search(idEvento, idUsuario, status, ini, fim, PageRequest.of(page, size))
//               .stream()
//               .map(e -> new Inscricao(e.getId(), e.getEventoId(), e.getUsuarioId(), e.getStatus(), e.getData()))
//               .toList();
//   }

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
