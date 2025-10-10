package com.eventosapi.evento.application.port;

import java.util.List;

import org.springframework.data.domain.Page;

import com.eventosapi.evento.domain.enums.TipoUsuario;
import com.eventosapi.evento.domain.model.Inscricao;

public interface InscricaoClientPort {

    Page<Inscricao> findAll(Long id, String nome, String email, String telefone, TipoUsuario tipo, int pageNumber, int pageSize);

    Inscricao findById(Long id);

    List<Inscricao> findByEventoId(Long id);
}
