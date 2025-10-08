package com.eventosapi.inscricao.repository;

import com.eventosapi.inscricao.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioReadRepository extends JpaRepository<Usuario, Long> {}