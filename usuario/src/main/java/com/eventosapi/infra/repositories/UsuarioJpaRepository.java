package com.eventosapi.infra.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eventosapi.infra.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long>, JpaSpecificationExecutor<UsuarioEntity> {
    Boolean existsByEmail(String email);
    Optional<UsuarioEntity> findByEmail(String email);
}