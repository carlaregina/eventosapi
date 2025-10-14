package com.eventosapi.comunicacoes.application.services;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.eventosapi.comunicacoes.application.dtos.InscricaoDTO;
import com.eventosapi.comunicacoes.application.port.EventoClientPort;
import com.eventosapi.comunicacoes.application.port.UsuarioClientPort;
import com.eventosapi.comunicacoes.domain.model.Email;
import com.eventosapi.comunicacoes.domain.model.Evento;
import com.eventosapi.comunicacoes.domain.model.Inscricao;
import com.eventosapi.comunicacoes.domain.model.Usuario;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoucherService {
    
    private final PDFService pdfService;
    private final EmailService emailService;
    private final EventoClientPort eventoClient;
    private final UsuarioClientPort usuarioClient;

    public void enviarVoucherInscricao(InscricaoDTO dto) {
        Inscricao inscricao = from(dto);
        byte[] pdf = pdfService.geraRelatorioPDF(inscricao);
        emailService.enviar(Email.inscricaoRealizada(inscricao.getUsuario().getEmail(), Map.of("voucher.pdf", pdf)));
    }

    public void enviarVoucherAtualizado(InscricaoDTO dto) {
        Inscricao inscricao = from(dto);
        byte[] pdf = pdfService.geraRelatorioPDF(inscricao);
        emailService.enviar(Email.eventoAtualizado(inscricao.getUsuario().getEmail(), Map.of("voucher.pdf", pdf)));
    }

    private Inscricao from(InscricaoDTO dto) {
        Evento evento = buscarEventoPorId(dto.getIdEvento());
        Usuario usuario = buscarUsuarioPorId(dto.getIdUsuario());
        return new Inscricao(dto.getId(), evento, usuario, dto.getData(), dto.getStatus());
    }

    private Usuario buscarUsuarioPorId(Long id) {
        return usuarioClient.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
    }

    private Evento buscarEventoPorId(Long id) {
        return eventoClient.findById(id)
            .orElseThrow(() -> new RuntimeException("Evento não encontrado com ID: " + id));
    }
}
