package com.eventosapi.comunicacoes.infra.client;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.eventosapi.comunicacoes.application.port.EventoClientPort;
import com.eventosapi.comunicacoes.domain.enums.TipoEvento;
import com.eventosapi.comunicacoes.domain.model.Evento;

@Component
@Profile("mock") // ativa apenas no profile mock
public class EventoClientMock implements EventoClientPort {

    @Override
    public Evento findById(Long id) {
        Evento e = new Evento();
        e.setId(id);
        e.setTitulo("Evento Mock " + id);
        e.setDescricao("Descrição simulada do evento " + id);
        e.setData(LocalDateTime.now().plusDays(3));
        e.setTipo(TipoEvento.CURSO);
        e.setMaxParticipantes(100);
        e.setOrganizadorId(1L);
        e.setLocalId(10L);
        return e;
    }
}
