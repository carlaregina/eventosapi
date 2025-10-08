package com.eventosapi.evento.infra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventosapi.evento.infra.entity.UsuarioEntity;

@Repository
public interface UserJpaRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByEmail(String subject);
}
