package com.eventosapi.evento.infra.client;

import com.eventosapi.evento.application.port.InscricaoClientPort;
import com.eventosapi.evento.domain.enums.StatusInscricao;
import com.eventosapi.evento.domain.enums.TipoUsuario;
import com.eventosapi.evento.domain.model.Evento;
import com.eventosapi.evento.domain.model.Inscricao;
import com.eventosapi.evento.domain.model.Usuario;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Profile("mock") // ativa só com profile "mock"
public class InscricaoClientMock implements InscricaoClientPort {

    @Override
    public Page<Inscricao> findAll(Long id, String nome, String email, String telefone, TipoUsuario tipo, int pageNumber, int pageSize) {
        // Usuários mock
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setNome("Maria Mock");
        usuario1.setEmail("maria.mock@email.com");

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNome("João Mock");
        usuario2.setEmail("joao.mock@email.com");

        // Eventos mock
        Evento evento1 = new Evento();
        evento1.setId(1L);
        evento1.setTitulo("Evento de Tecnologia");

        Evento evento2 = new Evento();
        evento2.setId(2L);
        evento2.setTitulo("Workshop Spring Boot");

        // Inscrições mock
        Inscricao i1 = new Inscricao();
        i1.setId(1L);
        i1.setUsuario(usuario1);
        i1.setEvento(evento1);
        i1.setData(LocalDateTime.now().minusDays(1));
        i1.setStatus(StatusInscricao.CONFIRMADA);

        Inscricao i2 = new Inscricao();
        i2.setId(2L);
        i2.setUsuario(usuario2);
        i2.setEvento(evento2);
        i2.setData(LocalDateTime.now());
        i2.setStatus(StatusInscricao.PENDENTE);

        List<Inscricao> lista = List.of(i1, i2);
        return new PageImpl<>(lista);
    }

    @Override
    public Inscricao findById(Long id) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNome("Usuário Mock " + id);
        usuario.setEmail("mock" + id + "@email.com");

        Evento evento = new Evento();
        evento.setId(id);
        evento.setTitulo("Evento Mock " + id);

        Inscricao inscricao = new Inscricao();
        inscricao.setId(id);
        inscricao.setUsuario(usuario);
        inscricao.setEvento(evento);
        inscricao.setData(LocalDateTime.now());
        inscricao.setStatus(StatusInscricao.CONFIRMADA);

        return inscricao;
    }

    @Override
    public List<Inscricao> findByEventoId(Long eventoId) {
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setNome("Maria Mock");
        usuario1.setEmail("maria.mock@email.com");

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNome("João Mock");
        usuario2.setEmail("joao.mock@email.com");

        Evento evento = new Evento();
        evento.setId(eventoId);
        evento.setTitulo("Evento Mock " + eventoId);

        Inscricao i1 = new Inscricao();
        i1.setId(1L);
        i1.setUsuario(usuario1);
        i1.setEvento(evento);
        i1.setData(LocalDateTime.now().minusDays(1));
        i1.setStatus(StatusInscricao.CONFIRMADA);

        Inscricao i2 = new Inscricao();
        i2.setId(2L);
        i2.setUsuario(usuario2);
        i2.setEvento(evento);
        i2.setData(LocalDateTime.now());
        i2.setStatus(StatusInscricao.PENDENTE);

        return List.of(i1, i2);
    }
}
