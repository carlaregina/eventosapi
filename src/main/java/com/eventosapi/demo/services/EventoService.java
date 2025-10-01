package com.eventosapi.demo.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventosapi.demo.dtos.EventoRequestDTO;
import com.eventosapi.demo.dtos.EventoResponseDTO;
import com.eventosapi.demo.dtos.FiltroEventoDTO;
import com.eventosapi.demo.exceptions.EntidadeNaoEncontradoException;
import com.eventosapi.demo.models.Evento;
import com.eventosapi.demo.models.Local;
import com.eventosapi.demo.models.Usuario;
import com.eventosapi.demo.repositories.EventoRepository;
import com.eventosapi.demo.repositories.LocalRepository;
import com.eventosapi.demo.repositories.UsuarioRepository;
import com.eventosapi.demo.specifications.EventoSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LocalRepository localRepository;

    @Transactional(readOnly = true)
    public Page<EventoResponseDTO> listar(FiltroEventoDTO filtro, Pageable pageable) {
        Specification<Evento> specification = EventoSpecification.build()
            .and(EventoSpecification.comTitulo(filtro.getTitulo()))
            .and(EventoSpecification.comDescricao(filtro.getDescricao()))
            .and(EventoSpecification.comData(filtro.getData()))
            .and(EventoSpecification.comDataMaiorQue(filtro.getDataMaiorQue()))
            .and(EventoSpecification.comDataMenorQue(filtro.getDataMenorQue()))
            .and(EventoSpecification.comTipos(filtro.getTipos()))
            .and(EventoSpecification.comOrganizadorId(filtro.getOrganizadorId()))
            .and(EventoSpecification.comLocalId(filtro.getLocalId()));
        return eventoRepository.findAll(specification, pageable).map(this::toResponseDTO);
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
            .organizadorNome(evento.getOrganizador().getNome())
            .localId(evento.getLocal().getId())
            .localNome(evento.getLocal().getNome())
            .build();
    }
}
