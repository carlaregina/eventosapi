package com.eventosapi.inscricao.application.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eventosapi.inscricao.application.dtos.FiltroInscricaoDTO;
import com.eventosapi.inscricao.application.exception.EntidadeNaoEncontradoException;
import com.eventosapi.inscricao.application.exception.RegraNegocioException;
import com.eventosapi.inscricao.application.port.EventoClientPort;
import com.eventosapi.inscricao.application.port.InscricaoRepositoryPort;
import com.eventosapi.inscricao.application.port.UsuarioClientPort;
import com.eventosapi.inscricao.domain.enums.StatusInscricao;
import com.eventosapi.inscricao.domain.models.Inscricao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InscricaoService {

    private final InscricaoRepositoryPort inscricaoRepo;
    private final EventoClientPort eventoPort;
    private final UsuarioClientPort usuarioPort;

    public Inscricao salvar(Inscricao inscricao) {
        if (inscricaoRepo.existsByEventoAndUsuario(inscricao.getEventoId(), inscricao.getUsuarioId())) {
            throw new RegraNegocioException("Usuário já inscrito neste evento.");
        }

        var confirmadas = inscricaoRepo.countConfirmadasByEvento(inscricao.getEventoId());
        var evento = eventoPort.findById(inscricao.getEventoId())
            .orElseThrow(() -> new EntidadeNaoEncontradoException("Evento não encontrado"));
        
        
        if (confirmadas >= evento.getMaxParticipantes()) {
            throw new RegraNegocioException("Capacidade esgotada.");
        }
            
        if(!usuarioPort.existsById(inscricao.getUsuarioId())) {
            throw new EntidadeNaoEncontradoException("Usuário não encontrado");
        }

        return inscricaoRepo.save(inscricao);
    }

    public Page<Inscricao> listar(FiltroInscricaoDTO filtro, Pageable pageable) {
        return inscricaoRepo.findAll(filtro, pageable);
    }

    public Inscricao buscarPorId(Long id) {
        return inscricaoRepo.findById(id)
            .orElseThrow(() -> new EntidadeNaoEncontradoException("Inscrição não encontrada"));
    }

    public Inscricao atualizarStatus(Long id, StatusInscricao status) {
        Inscricao inscricao = inscricaoRepo.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradoException("Inscrição não encontrada"));
        inscricao.setStatus(status);
        return inscricaoRepo.save(inscricao);
    }

    public void excluir(Long id) {
        atualizarStatus(id, StatusInscricao.CANCELADO);
    }

}
