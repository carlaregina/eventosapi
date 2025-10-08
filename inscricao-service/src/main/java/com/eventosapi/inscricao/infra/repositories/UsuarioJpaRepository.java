package com.eventosapi.inscricao.infra.repositories;

import com.eventosapi.inscricao.infra.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {}
