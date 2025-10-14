package com.eventosapi.comunicacoes.infra.client;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.eventosapi.comunicacoes.application.port.LocalClientPort;
import com.eventosapi.comunicacoes.domain.enums.Estado;
import com.eventosapi.comunicacoes.domain.enums.TipoLocal;
import com.eventosapi.comunicacoes.domain.model.Local;

@Component
@Profile("mock") // ativa só com profile "mock"
public class LocalClientMock implements LocalClientPort {

    @Override
    public List<Local> findAll() {
        Local local1 = new Local();
        local1.setId(1L);
        local1.setNome("Auditório Central");
        local1.setCidade("São Paulo");
        local1.setEstado(Estado.SP);
        local1.setTipo(TipoLocal.COMERCIAL);

        Local local2 = new Local();
        local1.setId(2L);
        local2.setNome("Sala de Conferência");
        local2.setCidade("Rio de Janeiro");
        local2.setEstado(Estado.RJ);
        local2.setTipo(TipoLocal.PARQUE);

        return List.of(local1, local2);
    }

    @Override
    public Local findById(Long id) {
        Local local = new Local();
        local.setId(1L);
        local.setNome("Local Mock " + id);
        local.setCidade("Cidade Mock");
        local.setEstado(Estado.SP);
        local.setTipo(TipoLocal.RESIDENCIAL);
        return local;
    }
}
