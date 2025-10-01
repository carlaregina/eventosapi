package com.eventosapi.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventosapi.demo.dtos.InscricaoRequestDTO;
import com.eventosapi.demo.enums.StatusInscricao;
import com.eventosapi.demo.exceptions.DuplicidadeInscricaoException;
import com.eventosapi.demo.exceptions.RecursoNaoEncontradoException;
import com.eventosapi.demo.models.Evento;
import com.eventosapi.demo.models.Inscricao;
import com.eventosapi.demo.models.Usuario;
import com.eventosapi.demo.repositories.EventoRepository;
import com.eventosapi.demo.repositories.InscricaoRepository;
import com.eventosapi.demo.repositories.UsuarioRepository;


@Service
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;
    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;
    private final VoucherService voucherService;
    private final EmailService emailService;

    public InscricaoService(InscricaoRepository inscricaoRepository, EventoRepository eventoRepository, UsuarioRepository usuarioRepository,
    EmailService emailService, VoucherService voucherService) {
        this.inscricaoRepository = inscricaoRepository;
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
        this.voucherService = voucherService;
        this.emailService = emailService;    
    }

    @Transactional
    public Inscricao criar(InscricaoRequestDTO req) {
        if (inscricaoRepository.existsById(req.id())) {
            throw new DuplicidadeInscricaoException("Usuário já inscrito neste evento.");
        }

        Evento evento = eventoRepository.findById(req.idEvento())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));
        Usuario usuario = usuarioRepository.findById(req.idUsuario())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

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
            .orElseThrow(() -> new RecursoNaoEncontradoException("Inscrição não encontrada"));
    }

    @Transactional(readOnly = true)
    public List<Inscricao> listar(Long idEvento) {
        if (idEvento != null) return inscricaoRepository.findByEventoId(idEvento);
        return inscricaoRepository.findAll();
    }

    @Transactional
    public Inscricao atualizarStatus(Long id, StatusInscricao status) {
        var i = buscar(id);
        i.setStatus(status);   
        return i;
    }

    @Transactional
    public void excluir(Long id) {
        inscricaoRepository.delete(buscar(id));
    }
}
