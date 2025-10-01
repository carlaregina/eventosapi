package com.eventosapi.demo.services;

import com.eventosapi.demo.dtos.EventoRequestDTO;
import com.eventosapi.demo.dtos.EventoResponseDTO;
import com.eventosapi.demo.dtos.FiltroEventoDTO;
import com.eventosapi.demo.exceptions.EntidadeNaoEncontradoException;
import com.eventosapi.demo.models.Evento;
import com.eventosapi.demo.models.Local;
import com.eventosapi.demo.models.Usuario;
import com.eventosapi.demo.dtos.UsuarioResponseDTO;
import com.eventosapi.demo.repositories.EventoRepository;
import com.eventosapi.demo.repositories.LocalRepository;
import com.eventosapi.demo.repositories.UsuarioRepository;
import com.eventosapi.demo.specifications.EventoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LocalRepository localRepository;

    public EventoService(EventoRepository eventoRepository,
                         UsuarioRepository usuarioRepository,
                         LocalRepository localRepository) {
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
        this.localRepository = localRepository;
    }

    @Transactional(readOnly = true)
    public List<EventoResponseDTO> listarTodos() {
        return eventoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EventoResponseDTO buscarPorId(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradoException("Evento não encontrado com ID: " + id));
        return toResponseDTO(evento);
    }

    @Transactional
    public EventoResponseDTO criar(EventoRequestDTO dto) {
        Usuario organizador = usuarioRepository.findById(dto.getOrganizadorId())
                .orElseThrow(() -> new EntidadeNaoEncontradoException("Organizador não encontrado com ID: " + dto.getOrganizadorId()));

        Local local = localRepository.findById(dto.getLocalId())
                .orElseThrow(() -> new EntidadeNaoEncontradoException("Local não encontrado com ID: " + dto.getLocalId()));

        Evento evento = Evento.builder()
                .titulo(dto.getTitulo())
                .descricao(dto.getDescricao())
                .data(dto.getData())
                .tipo(dto.getTipo())
                .maxParticipantes(dto.getMaxParticipantes())
                .organizador(organizador)
                .local(local)
                .build();

        Evento salvo = eventoRepository.save(evento);
        return toResponseDTO(salvo);
    }

    @Transactional
    public EventoResponseDTO atualizar(Long id, EventoRequestDTO dto) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradoException("Evento não encontrado com ID: " + id));

        Usuario organizador = usuarioRepository.findById(dto.getOrganizadorId())
                .orElseThrow(() -> new EntidadeNaoEncontradoException("Organizador não encontrado com ID: " + dto.getOrganizadorId()));

        Local local = localRepository.findById(dto.getLocalId())
                .orElseThrow(() -> new EntidadeNaoEncontradoException("Local não encontrado com ID: " + dto.getLocalId()));

        evento.setTitulo(dto.getTitulo());
        evento.setDescricao(dto.getDescricao());
        evento.setData(dto.getData());
        evento.setTipo(dto.getTipo());
        evento.setMaxParticipantes(dto.getMaxParticipantes());
        evento.setOrganizador(organizador);
        evento.setLocal(local);

        Evento atualizado = eventoRepository.save(evento);
        return toResponseDTO(atualizado);
    }

    @Transactional
    public void deletar(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradoException("Evento não encontrado com ID: " + id));
        eventoRepository.delete(evento);
    }

    private EventoResponseDTO toResponseDTO(Evento evento) {
        return EventoResponseDTO.builder()
                .titulo(evento.getTitulo())
                .descricao(evento.getDescricao())
                .data(evento.getData())
                .tipo(evento.getTipo())
                .maxParticipantes(evento.getMaxParticipantes())
                .organizadorId(evento.getOrganizador().getId())
                .localId(evento.getLocal().getId())
                .build();
    }

    public Page<UsuarioResponseDTO> listarUsuariosPorEvento(FiltroEventoDTO eventoFiltro, Pageable pageable) {
        Specification<Evento> specification = EventoSpecification.build()
            .and(EventoSpecification.comTitulo(eventoFiltro.getTitulo()))
            .and(EventoSpecification.comDescricao(eventoFiltro.getDescricao()))
            .and(EventoSpecification.comTipo(eventoFiltro.getTipo()));

        return eventoRepository.findAll(specification, pageable)
            .map(evento -> new UsuarioResponseDTO(
                evento.getOrganizador().getNome(), evento.getOrganizador().getEmail(), evento.getOrganizador().getTelefone(), evento.getOrganizador().getTipo()
                ));
    }
}