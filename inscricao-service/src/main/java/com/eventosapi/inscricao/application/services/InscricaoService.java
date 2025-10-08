package com.eventosapi.inscricao.application.services;

import com.eventosapi.inscricao.application.port.EventoReadPort;
import com.eventosapi.inscricao.application.port.InscricaoRepositoryPort;
import com.eventosapi.inscricao.application.port.UsuarioReadPort;
import com.eventosapi.inscricao.domain.enums.StatusInscricao;
import com.eventosapi.inscricao.domain.models.Inscricao;
import com.eventosapi.inscricao.interfaces.dto.FiltroInscricaoDTO;
import com.eventosapi.inscricao.interfaces.dto.InscricaoRequestDTO; 
import com.eventosapi.inscricao.interfaces.dto.InscricaoResponseDTO;
import com.eventosapi.inscricao.exception.EntidadeNaoEncontradoException;
import com.eventosapi.inscricao.exception.RegraNegocioException;    


import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;    



@Service
@RequiredArgsConstructor
public class InscricaoService {

  private final InscricaoRepositoryPort inscricaoRepo;
  private final EventoReadPort eventoPort;
  private final UsuarioReadPort usuarioPort;

  @Transactional
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
        saved.getId(), saved.getStatus(),
        evento.getTitulo(), usuario.getNome(),
        saved.getData()
    );
  }

  @Transactional(readOnly = true)
  public InscricaoResponseDTO buscar(Long id) {
    var i = inscricaoRepo.findById(id)
        .orElseThrow(() -> new EntidadeNaoEncontradoException("Inscrição não encontrada"));

    var evento = eventoPort.findById(i.getEventoId())
        .orElseThrow(() -> new EntidadeNaoEncontradoException("Evento não encontrado"));
    var usuario = usuarioPort.findById(i.getUsuarioId())
        .orElseThrow(() -> new EntidadeNaoEncontradoException("Usuário não encontrado"));

    return new InscricaoResponseDTO(i.getId(), i.getStatus(),
        evento.getTitulo(), usuario.getNome(), i.getData());
  }

  @Transactional(readOnly = true)
  public List<InscricaoResponseDTO> listar(FiltroInscricaoDTO f) {
    var lista = inscricaoRepo.findAll(
        f.idEvento(), f.idUsuario(), f.status(), f.dataInicio(), f.dataFim(),
        f.page() == null ? 0 : f.page(), f.size() == null ? 10 : f.size()
    );

    // carrega nomes/títulos em lote? simples aqui, resolve 1 a 1
    return lista.stream().map(i -> {
      var ev = eventoPort.findById(i.getEventoId()).orElseThrow();
      var us = usuarioPort.findById(i.getUsuarioId()).orElseThrow();
      return new InscricaoResponseDTO(i.getId(), i.getStatus(), ev.getTitulo(), us.getNome(), i.getData());
    }).toList();
  }

  @Transactional
  public InscricaoResponseDTO atualizarStatus(Long id, StatusInscricao novo) {
    var i = inscricaoRepo.findById(id)
        .orElseThrow(() -> new EntidadeNaoEncontradoException("Inscrição não encontrada"));
    i.alterarStatus(novo);
    var saved = inscricaoRepo.save(i);

    var ev = eventoPort.findById(saved.getEventoId()).orElseThrow();
    var us = usuarioPort.findById(saved.getUsuarioId()).orElseThrow();

    return new InscricaoResponseDTO(saved.getId(), saved.getStatus(), ev.getTitulo(), us.getNome(), saved.getData());
  }

  @Transactional
  public void excluir(Long id) {
    var i = inscricaoRepo.findById(id)
        .orElseThrow(() -> new EntidadeNaoEncontradoException("Inscrição não encontrada"));
    // como o port não tem delete, persistimos "exclusão" salvando estado? Se quiser físico, adicione delete(id) no port + adapter
    // por enquanto, implementa no Adapter usando JpaRepository.deleteById(id)
    inscricaoRepo.save(new Inscricao(i.getId(), i.getEventoId(), i.getUsuarioId(), StatusInscricao.CANCELADO, i.getData()));
  }
}
