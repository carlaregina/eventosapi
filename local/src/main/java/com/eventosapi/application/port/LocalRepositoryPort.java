package com.eventosapi.application.port;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.eventosapi.domain.models.Local;
import com.eventosapi.interfaces.dtos.FiltroLocalDTO;

public interface LocalRepositoryPort {
    Optional<Local> buscarPorId(Long id);
    Page<Local> listar(FiltroLocalDTO filtro, Pageable pageable);
    Local salvar(Local local);
    void deletar(Long id);
}