package com.eventosapi.inscricao.application.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.eventosapi.inscricao.application.exception.EntidadeNaoEncontradoException;
import com.eventosapi.inscricao.application.exception.RegraNegocioException;
import com.eventosapi.inscricao.application.port.EventoReadPort;
import com.eventosapi.inscricao.application.port.InscricaoRepositoryPort;
import com.eventosapi.inscricao.application.port.UsuarioReadPort;
import com.eventosapi.inscricao.domain.enums.StatusInscricao;
import com.eventosapi.inscricao.domain.models.Inscricao;
import com.eventosapi.inscricao.interfaces.dto.FiltroInscricaoDTO;
import com.eventosapi.inscricao.interfaces.dto.InscricaoRequestDTO;
import com.eventosapi.inscricao.interfaces.dto.InscricaoResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InscricaoService {

  private final InscricaoRepositoryPort inscricaoRepo;
  private final EventoReadPort eventoPort;
  private final UsuarioReadPort usuarioPort;

  public InscricaoResponseDTO criar(InscricaoRequestDTO dto) {
    if (inscricaoRepo.existsByEventoAndUsuario(dto.idEvento(), dto.idUsuario()))
      throw new RegraNegocioException("Usuário já inscrito neste evento.");

    var evento = eventoPort.findById(dto.idEvento())
        .orElseThrow(() -> new EntidadeNaoEncontradoException("Evento não encontrado"));
    var usuario = usuarioPort.findById(dto.idUsuario())
        .orElseThrow(() -> new EntidadeNaoEncontradoException("Usuário não encontrado"));

    long confirmadas = inscricaoRepo.countConfirmadasByEvento(evento.getId());
    if (confirmadas >= evento.getMaxParticipantes())
      throw new RegraNegocioException("Capacidade esgotada.");

    var status = dto.status() == null ? StatusInscricao.PENDENTE : dto.status();
    var nova = Inscricao.nova(evento.getId(), usuario.getId(), status, LocalDateTime.now());
    var saved = inscricaoRepo.save(nova);

   return new InscricaoResponseDTO(
        saved.getId(),
        saved.getEventoId(),
        saved.getUsuarioId(),
        saved.getStatus(),
        saved.getData()
    );
  }

  public InscricaoResponseDTO buscar(Long id) {
    var i = inscricaoRepo.findById(id)
        .orElseThrow(() -> new EntidadeNaoEncontradoException("Inscrição não encontrada"));


     return new InscricaoResponseDTO(
        i.getId(),
        i.getEventoId(),
        i.getUsuarioId(),
        i.getStatus(),
        i.getData()
    );
  }

  public List<InscricaoResponseDTO> listar(FiltroInscricaoDTO f) {
    var lista = inscricaoRepo.findAll(
        f.idEvento(), f.idUsuario(), f.status(), f.dataInicio(), f.dataFim(),
        f.page() == null ? 0 : f.page(), f.size() == null ? 10 : f.size()
    );

   
     return lista.stream()
        .map(i -> new InscricaoResponseDTO(
            i.getId(),
            i.getEventoId(),
            i.getUsuarioId(),
            i.getStatus(),
            i.getData()
        ))
        .toList();
  }

  public List<InscricaoResponseDTO> listarConfirmadasPorEvento(Long eventoId, Integer page, Integer size) {

    var lista = inscricaoRepo.findByEventoAndStatus(
        eventoId,
        StatusInscricao.CONFIRMADA,
        page == null ? 0 : page,
        size == null ? 10 : size
    );

    return lista.stream()
        .map(i -> new InscricaoResponseDTO(
            i.getId(),
            i.getEventoId(),
            i.getUsuarioId(),
            i.getStatus(),
            i.getData()
        ))
        .toList();
  }

  public InscricaoResponseDTO atualizarStatus(Long id, StatusInscricao novoStatus) {
    var existente = inscricaoRepo.findById(id)
        .orElseThrow(() -> new EntidadeNaoEncontradoException("Inscrição não encontrada"));

    var atualizado = new Inscricao(
        existente.getId(),
        existente.getEventoId(),
        existente.getUsuarioId(),
        novoStatus,
        existente.getData()
    );

    var saved = inscricaoRepo.save(atualizado);

    return new InscricaoResponseDTO(
        saved.getId(),
        saved.getEventoId(),
        saved.getUsuarioId(),
        saved.getStatus(),
        saved.getData()
    );
  }

  public void excluir(Long id) {
    var existente = inscricaoRepo.findById(id)
        .orElseThrow(() -> new EntidadeNaoEncontradoException("Inscrição não encontrada"));

    var cancelado = new Inscricao(
        existente.getId(),
        existente.getEventoId(),
        existente.getUsuarioId(),
        StatusInscricao.CANCELADO,
        existente.getData()
    );

    inscricaoRepo.save(cancelado);
  }



}
