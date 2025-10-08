package com.eventosapi.evento.application.services;

import com.eventosapi.evento.application.port.*;
import com.eventosapi.evento.domain.model.Evento;
import com.eventosapi.evento.domain.model.Inscricao;
import com.eventosapi.evento.domain.model.Local;
import com.eventosapi.evento.domain.model.Usuario;
import com.eventosapi.evento.interfaces.dto.*;
import com.eventosapi.evento.interfaces.specification.EventoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventoService {

    private final EventoRepositoryPort repository;
    private final UsuarioClientPort usuarioClient;
    private final LocalClientPort localClient;
    private final InscricaoClientPort inscricaoClient;
    private final EventoPublisherPort eventoPublisherPort;

    public EventoService(EventoRepositoryPort eventoRepositoryPort, UsuarioClientPort usuarioClient, LocalClientPort localClient, InscricaoClientPort inscricaoClient, EventoPublisherPort eventoPublisherPort) {
        this.repository = eventoRepositoryPort;
        this.usuarioClient = usuarioClient;
        this.localClient = localClient;
        this.inscricaoClient = inscricaoClient;
        this.eventoPublisherPort = eventoPublisherPort;
    }


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
        Evento evento = repository.findById(id);
        return toResponseDTO(evento);
    }

    @Transactional
    public EventoResponseDTO criar(EventoRequestDTO dto) {
        Usuario organizador = usuarioClient.findById(dto.getOrganizadorId());
        Local local = localClient.findById(dto.getLocalId());

        if (local == null) {
            throw new IllegalArgumentException("Local não encontrado para o id: " + dto.getLocalId());
        }

        Evento evento = new Evento();
        evento.setTitulo(dto.getTitulo());
        evento.setDescricao(dto.getDescricao());
        evento.setData(dto.getData());
        evento.setMaxParticipantes(dto.getMaxParticipantes());
        evento.setTipo(dto.getTipo());
        evento.setOrganizadorId(organizador.getId());
        evento.setLocalId(local.getId());

        Evento salvo = repository.save(evento);
        return toResponseDTO(salvo);
    }

    @Transactional
    public EventoResponseDTO atualizar(Long id, EventoRequestDTO dto) {
        Evento evento = repository.findById(id);

        Usuario organizador = usuarioClient.findById(dto.getOrganizadorId());
        Local local = localClient.findById(dto.getLocalId());

        evento.setTitulo(dto.getTitulo());
        evento.setDescricao(dto.getDescricao());
        evento.setData(dto.getData());
        evento.setTipo(dto.getTipo());
        evento.setMaxParticipantes(dto.getMaxParticipantes());
        evento.setOrganizadorId(organizador.getId());
        evento.setLocalId(local.getId());

        Evento atualizado = repository.save(evento);

        enviarPDFAtualizado(atualizado);

        return toResponseDTO(atualizado);
    }

    private void enviarPDFAtualizado(Evento atualizado) {
        List<Inscricao> inscricoes = inscricaoClient.findByEventoId(atualizado.getId());

        for (Inscricao inscricao : inscricoes) {
            InscricaoDTO dto = new InscricaoDTO();
            dto.setId(inscricao.getId());
            dto.setEvento(inscricao.getEvento());
            dto.setUsuario(inscricao.getUsuario());
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

//    public Page<UsuarioResponseDTO> listarUsuariosPorEvento(Long id, FiltroUsuarioDTO filtro, Pageable pageable) {
//        Specification<Inscricao> specification = InscricaoSpecification.build()
//                .and(InscricaoSpecification.comEventoId(id))
//                .and(InscricaoSpecification.comUsuarioNome(filtro.getNome()))
//                .and(InscricaoSpecification.comUsuarioEmail(filtro.getEmail()))
//                .and(InscricaoSpecification.comUsuarioTelefone(filtro.getTelefone()))
//                .and(InscricaoSpecification.comUsuarioTipo(filtro.getTipo()));
//
//        Page<Inscricao> inscricoes = inscricaoClient.findAll(filtro, specification, pageable);
//
//        return inscricoes.map(Inscricao::getUsuario)
//                .map(usuario -> new UsuarioResponseDTO(
//                        usuario.getNome(),
//                        usuario.getEmail(),
//                        usuario.getTelefone(),
//                        usuario.getTipo()
//                ));
//    }

    public Page<UsuarioResponseDTO> listarUsuariosPorEvento(Long id, FiltroUsuarioDTO filtro, Pageable pageable) {
        // Passar os filtros e paginação como parâmetros
        Page<Inscricao> inscricoes = inscricaoClient.findAll(
                id,
                filtro.getNome(),
                filtro.getEmail(),
                filtro.getTelefone(),
                filtro.getTipo(),
                pageable.getPageNumber(),
                pageable.getPageSize()
        );

        return inscricoes.map(Inscricao::getUsuario)
                .map(usuario -> new UsuarioResponseDTO(
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getTelefone(),
                        usuario.getTipo()
                ));
    }



}
