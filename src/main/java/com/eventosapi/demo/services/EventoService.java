package com.eventosapi.demo.services;

import com.eventosapi.demo.models.Inscricao;
import com.eventosapi.demo.repositories.InscricaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.eventosapi.demo.dtos.EventoRequestDTO;
import com.eventosapi.demo.dtos.EventoResponseDTO;
import com.eventosapi.demo.dtos.FiltroEventoDTO;
import com.eventosapi.demo.dtos.FiltroUsuarioDTO;
import com.eventosapi.demo.exceptions.EntidadeNaoEncontradoException;
import com.eventosapi.demo.models.Evento;
import com.eventosapi.demo.models.Local;
import com.eventosapi.demo.models.Usuario;
import com.eventosapi.demo.dtos.UsuarioResponseDTO;
import com.eventosapi.demo.repositories.EventoRepository;
import com.eventosapi.demo.repositories.LocalRepository;
import com.eventosapi.demo.repositories.UsuarioRepository;
import com.eventosapi.demo.specifications.EventoSpecification;
import com.eventosapi.demo.specifications.InscricaoSpecification;
import com.eventosapi.demo.specifications.UsuarioSpecification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LocalRepository localRepository;
    private final InscricaoRepository inscricaoRepository;
    private final EmailService emailService;

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

        EventoResponseDTO atualizadoDTO = toResponseDTO(atualizado);
        enviaEmailDeAtualizacao(atualizadoDTO, id);

        return atualizadoDTO;
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

    public Page<UsuarioResponseDTO> listarUsuariosPorEvento(Long id, FiltroUsuarioDTO filtro, Pageable pageable) {
        Specification<Inscricao> specification = InscricaoSpecification.build()
            .and(InscricaoSpecification.comEventoId(id))
            .and(InscricaoSpecification.comUsuarioNome(filtro.getNome()))
            .and(InscricaoSpecification.comUsuarioEmail(filtro.getEmail()))
            .and(InscricaoSpecification.comUsuarioTelefone(filtro.getTelefone()))
            .and(InscricaoSpecification.comUsuarioTipo(filtro.getTipo()));

        Page<Inscricao> inscricoes = inscricaoRepository.findAll(specification, pageable);

        return inscricoes.map(Inscricao::getUsuario)
            .map(usuario -> new UsuarioResponseDTO(
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getTipo()
            ));
    }

    public void enviaEmailDeAtualizacao(EventoResponseDTO eventoDTO, Long id) {
        Local local = localRepository.findById(eventoDTO.getLocalId()).orElseThrow();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String horaFormatada = eventoDTO.getData().format(formatter);

        List<Inscricao> inscricoes = inscricaoRepository.findByEventoId(id);

        String assunto = "Houve uma alteração no evento " + eventoDTO.getTitulo();
        String corpo = "Olá, o evento " + eventoDTO.getTitulo() + " foi atualizado.\n"
                + "Observe os novos detalhes:\n"
                + "Local: " + local.getNome() + " | Horário: " + horaFormatada;

        for (Inscricao inscricao : inscricoes) {
            String email = inscricao.getUsuario().getEmail();
            emailService.enviarComAnexo(email, assunto, corpo, inscricao);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }
}
