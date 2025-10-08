package com.eventosapi.inscricao.service;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.eventosapi.inscricao.domain.Inscricao;
import com.eventosapi.inscricao.domain.StatusInscricao;
import com.eventosapi.inscricao.dto.FiltroInscricaoDTO;
import com.eventosapi.inscricao.dto.InscricaoRequestDTO;
import com.eventosapi.inscricao.dto.InscricaoResponseDTO;
import com.eventosapi.inscricao.exception.EntidadeNaoEncontradoException;
import com.eventosapi.inscricao.exception.RegraNegocioException;
import com.eventosapi.inscricao.repository.EventoReadRepository;
import com.eventosapi.inscricao.repository.InscricaoRepository;
import com.eventosapi.inscricao.repository.InscricaoSpecifications;
import com.eventosapi.inscricao.repository.UsuarioReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;  



@Service
@RequiredArgsConstructor
public class InscricaoService {

  private final InscricaoRepository inscricaoRepo;
  private final EventoReadRepository eventoRepo;
  private final UsuarioReadRepository usuarioRepo;

  // --------- CREATE -> retorna DTO
  @Transactional
  public InscricaoResponseDTO criar(InscricaoRequestDTO dto) {
    if (inscricaoRepo.existsByEvento_IdAndUsuario_Id(dto.idEvento(), dto.idUsuario()))
      throw new RegraNegocioException("Usuário já inscrito neste evento.");

    var evento = eventoRepo.findById(dto.idEvento())
        .orElseThrow(() -> new EntidadeNaoEncontradoException("Evento não encontrado"));
    var usuario = usuarioRepo.findById(dto.idUsuario())
        .orElseThrow(() -> new EntidadeNaoEncontradoException("Usuário não encontrado"));

    long confirmadas = inscricaoRepo.countConfirmadasByEvento(evento.getId());
    if (confirmadas >= evento.getMaxParticipantes())
      throw new RegraNegocioException("Capacidade esgotada.");

    var i = Inscricao.builder()
        .evento(evento)
        .usuario(usuario)
        .status(Optional.ofNullable(dto.status()).orElse(StatusInscricao.PENDENTE))
        .data(LocalDateTime.now())
        .build();

    i = inscricaoRepo.save(i);

    // monta o DTO ainda dentro da transação, sem risco de LAZY
    return toResponse(i);
  }

  // --------- READ (por id) -> retorna DTO
  @Transactional(readOnly = true)
  public InscricaoResponseDTO buscar(Long id) {
    var i = inscricaoRepo.findById(id)
        .orElseThrow(() -> new EntidadeNaoEncontradoException("Inscrição não encontrada"));
    return toResponse(i);
  }

  // --------- LIST -> retorna Page<DTO>
  @Transactional(readOnly = true)
  public Page<InscricaoResponseDTO> listar(FiltroInscricaoDTO filtro, Pageable pageable) {
    var page = inscricaoRepo.findAll(InscricaoSpecifications.from(filtro), pageable);
    return page.map(this::toResponse);
  }

  // --------- UPDATE (status) -> retorna DTO
  @Transactional
  public InscricaoResponseDTO atualizarStatus(Long id, StatusInscricao novoStatus) {
    var i = inscricaoRepo.findById(id)
        .orElseThrow(() -> new EntidadeNaoEncontradoException("Inscrição não encontrada"));
    i.setStatus(novoStatus);
    i = inscricaoRepo.save(i);
    return toResponse(i);
  }

  // --------- DELETE
  @Transactional
  public void excluir(Long id) {
    var i = inscricaoRepo.findById(id)
        .orElseThrow(() -> new EntidadeNaoEncontradoException("Inscrição não encontrada"));
    inscricaoRepo.delete(i);
  }

  // --------- Mapper interno
  private InscricaoResponseDTO toResponse(Inscricao i) {
    return new InscricaoResponseDTO(
        i.getId(),
        i.getStatus(),
        i.getEvento().getTitulo(),
        i.getUsuario().getNome(),
        i.getData()
    );
  }
}