package com.eventosapi.evento.application.port;

import com.eventosapi.evento.domain.enums.TipoUsuario;
import com.eventosapi.evento.domain.model.Inscricao;
import com.eventosapi.evento.interfaces.dto.FiltroUsuarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

public interface InscricaoClientPort {

    Page<Inscricao> findAll(Long id, String nome, String email, String telefone, TipoUsuario tipo, int pageNumber, int pageSize);

    Inscricao findById(Long id);
}
