package com.eventosapi.demo.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventosapi.demo.dtos.FiltroInscricaoDTO;
import com.eventosapi.demo.dtos.InscricaoRequestDTO;
import com.eventosapi.demo.dtos.InscricaoResponseDTO;
import com.eventosapi.demo.enums.StatusInscricao;
import com.eventosapi.demo.exceptions.DuplicidadeInscricaoException;
import com.eventosapi.demo.exceptions.EntidadeNaoEncontradoException;
import com.eventosapi.demo.models.Evento;
import com.eventosapi.demo.models.Inscricao;
import com.eventosapi.demo.models.Usuario;
import com.eventosapi.demo.repositories.EventoRepository;
import com.eventosapi.demo.repositories.InscricaoRepository;
import com.eventosapi.demo.repositories.UsuarioRepository;
import com.eventosapi.demo.specifications.InscricaoSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;
    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;
    private final VoucherService voucherService;
    private final EmailService emailService;

    @Transactional
    public Inscricao criar(InscricaoRequestDTO req) {
        if (inscricaoRepository.existsByEventoIdAndUsuarioId(req.idEvento(), req.idUsuario())) {
            throw new DuplicidadeInscricaoException("Usuário já inscrito neste evento.");
        }

        Evento evento = eventoRepository.findById(req.idEvento())
            .orElseThrow(() -> new EntidadeNaoEncontradoException("Evento não encontrado"));
        Usuario usuario = usuarioRepository.findById(req.idUsuario())
            .orElseThrow(() -> new EntidadeNaoEncontradoException("Usuário não encontrado"));

        Inscricao inscricao = Inscricao.builder()
            .evento(evento)
            .usuario(usuario)
            .status(StatusInscricao.CONFIRMADA)
            .build();

       inscricaoRepository.save(inscricao);

        
        byte[] pdf = voucherService.geraRelatorioPDF(req);

        String email = inscricao.getUsuario().getEmail();
        String assunto = "Confirmação de Inscrição";
        String corpo = "Olá " + inscricao.getUsuario().getNome() + ", segue seu voucher em anexo.";
        emailService.enviarComAnexo(email, assunto, corpo, pdf, "voucher.pdf");

        return inscricao;

    }

    @Transactional(readOnly = true)
    public Inscricao buscar(Long id) {
        return inscricaoRepository.findById(id)
            .orElseThrow(() -> new EntidadeNaoEncontradoException("Inscrição não encontrada"));
    }

    @Transactional(readOnly = true)
    public Page<InscricaoResponseDTO> listar(FiltroInscricaoDTO filtro, Pageable page) {
        Specification<Inscricao> specification = InscricaoSpecification.build()
            .and(InscricaoSpecification.comData(filtro.getData()))
            .and(InscricaoSpecification.comDataMenorQue(filtro.getDataMenorQue()))
            .and(InscricaoSpecification.comDataMaiorQue(filtro.getDataMaiorQue()))
            .and(InscricaoSpecification.comStatus(filtro.getStatus()))
            .and(InscricaoSpecification.comUsuarioId(filtro.getUsuarioId()))
            .and(InscricaoSpecification.comEventoId(filtro.getEventoId()));
        return inscricaoRepository.findAll(specification, page).map(InscricaoResponseDTO::from);
    }

    @Transactional
    public Inscricao atualizarStatus(Long id, StatusInscricao status) {
        var inscricao = buscar(id);
        inscricao.setStatus(status);   
        return inscricaoRepository.save(inscricao);
    }

    @Transactional
    public void excluir(Long id) {
        inscricaoRepository.delete(buscar(id));
    }
}
