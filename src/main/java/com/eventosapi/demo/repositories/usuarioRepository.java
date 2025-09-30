package com.eventosapi.demo.repositories;

import com.eventosapi.demo.models.usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface usuarioRepository extends JpaRepository<usuario, Long> {

}