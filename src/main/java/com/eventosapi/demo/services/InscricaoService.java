package com.eventosapi.demo.services;

import com.eventosapi.demo.enums.StatusInscricao;
import com.eventosapi.demo.exceptions.DuplicidadeInscricaoException;
import com.eventosapi.demo.exceptions.RecursoNaoEncontradoException;
import com.eventosapi.demo.models.Evento;
import com.eventosapi.demo.models.Inscricao;
import com.eventosapi.demo.models.Usuario;
import com.eventosapi.demo.repositories.EventoRepository;
import com.eventosapi.demo.repositories.InscricaoRepository;
import com.eventosapi.demo.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;
    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;

    public InscricaoService(InscricaoRepository inscricaoRepository, EventoRepository eventoRepository, UsuarioRepository usuarioRepository) {
        this.inscricaoRepository = inscricaoRepository;
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Inscricao criar(Long idEvento, Long idUsuario) {
        if (inscricaoRepository.existsByEventoIdAndUsuarioId(idEvento, idUsuario)) {
            throw new DuplicidadeInscricaoException("Usuário já inscrito neste evento.");
        }

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        Inscricao inscricao = Inscricao.builder()
                .evento(evento)
                .usuario(usuario)
                .status(StatusInscricao.CONFIRMADA)
                
                .build();

        return inscricaoRepository.save(inscricao);
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
