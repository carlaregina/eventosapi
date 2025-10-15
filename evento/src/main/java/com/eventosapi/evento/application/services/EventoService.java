package com.eventosapi.evento.application.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventosapi.evento.application.dtos.FiltroInscricaoDTO;
import com.eventosapi.evento.application.port.EventoPublisherPort;
import com.eventosapi.evento.application.port.EventoRepositoryPort;
import com.eventosapi.evento.application.port.InscricaoClientPort;
import com.eventosapi.evento.application.port.LocalClientPort;
import com.eventosapi.evento.application.port.UsuarioClientPort;
import com.eventosapi.evento.domain.model.Evento;
import com.eventosapi.evento.domain.model.Inscricao;
import com.eventosapi.evento.interfaces.dto.EventoRequestDTO;
import com.eventosapi.evento.interfaces.dto.EventoResponseDTO;
import com.eventosapi.evento.interfaces.dto.FiltroEventoDTO;
import com.eventosapi.evento.interfaces.dto.InscricaoDTO;
import com.eventosapi.evento.interfaces.dto.UsuarioResponseDTO;
import com.eventosapi.evento.interfaces.specification.EventoSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepositoryPort repository;
    private final UsuarioClientPort usuarioClient;
    private final LocalClientPort localClient;
    private final InscricaoClientPort inscricaoClient;
    private final EventoPublisherPort eventoPublisherPort;

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

        Page<EventoResponseDTO> dto = repository.findAll(specification, pageable)
                .map(this::toResponseDTO);
        return dto;
    }

    @Transactional(readOnly = true)
    public EventoResponseDTO buscarPorId(Long id) {
        System.out.println("ID do evento recebido no service: " + id);
       
        Evento evento = repository.findById(id);
        return toResponseDTO(evento);
    }

    @Transactional
    public EventoResponseDTO criar(EventoRequestDTO dto) {
        if (!usuarioClient.existsById(dto.getOrganizadorId())) {
            throw new IllegalArgumentException("Organizador não encontrado para o id: " + dto.getOrganizadorId());
        }
    
        if (!localClient.existsById(dto.getLocalId())) {
            throw new IllegalArgumentException("Local não encontrado para o id: " + dto.getLocalId());
        }

        Evento evento = new Evento();
        evento.setTitulo(dto.getTitulo());
        evento.setDescricao(dto.getDescricao());
        evento.setData(dto.getData());
        evento.setMaxParticipantes(dto.getMaxParticipantes());
        evento.setTipo(dto.getTipo());
        evento.setOrganizadorId(dto.getOrganizadorId());
        evento.setLocalId(dto.getLocalId());

        Evento salvo = repository.save(evento);
        return toResponseDTO(salvo);
    }

    @Transactional
    public EventoResponseDTO atualizar(Long id, EventoRequestDTO dto) {
        if (!usuarioClient.existsById(dto.getOrganizadorId())) {
            throw new IllegalArgumentException("Organizador não encontrado para o id: " + dto.getOrganizadorId());
        }
    
        if (!localClient.existsById(dto.getLocalId())) {
            throw new IllegalArgumentException("Local não encontrado para o id: " + dto.getLocalId());
        }

        Evento evento = repository.findById(id);
        evento.setTitulo(dto.getTitulo());
        evento.setDescricao(dto.getDescricao());
        evento.setData(dto.getData());
        evento.setTipo(dto.getTipo());
        evento.setMaxParticipantes(dto.getMaxParticipantes());
        evento.setOrganizadorId(dto.getOrganizadorId());
        evento.setLocalId(dto.getLocalId());

        Evento atualizado = repository.save(evento);

        enviarPDFAtualizado(atualizado);

        return toResponseDTO(atualizado);
    }

    private void enviarPDFAtualizado(Evento atualizado) {
        FiltroInscricaoDTO filtro = new FiltroInscricaoDTO();
        filtro.setEventoId(atualizado.getId());
        List<Inscricao> inscricoes = inscricaoClient.findAllByEventoId(filtro);

        for (Inscricao inscricao : inscricoes) {
            InscricaoDTO dto = new InscricaoDTO();
            dto.setId(inscricao.getId());
            dto.setIdEvento(inscricao.getIdEvento());
            dto.setIdUsuario(inscricao.getIdUsuario());
            dto.setData(inscricao.getData());
            dto.setStatus(inscricao.getStatus());

            eventoPublisherPort.publicarEvento(dto);
        }
    }

    @Transactional
    public void deletar(Long id) {
        Evento evento = repository.findById(id);
        repository.delete(evento);
    }

    private EventoResponseDTO toResponseDTO(Evento evento) {
        return EventoResponseDTO.builder()
                .titulo(evento.getTitulo())
                .descricao(evento.getDescricao())
                .data(evento.getData())
                .tipo(evento.getTipo())
                .maxParticipantes(evento.getMaxParticipantes())
                .organizadorId(evento.getOrganizadorId())
                .localId(evento.getLocalId())
                .build();
    }

    public Page<UsuarioResponseDTO> listarUsuariosPorEvento(Long eventoId, Pageable pageable) {
        FiltroInscricaoDTO filtro = new FiltroInscricaoDTO();
        filtro.setEventoId(eventoId);
        Page<Inscricao> inscricoes = inscricaoClient.findAllByEventoId(filtro, pageable);
        return inscricoes
            .map(Inscricao::getIdUsuario)
            .map(usuarioId -> usuarioClient.findById(usuarioId).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado para o id: " + usuarioId)))
            .map(usuario -> new UsuarioResponseDTO(usuario.getNome(), usuario.getEmail(), usuario.getTelefone(), usuario.getTipo()));
    }

}
