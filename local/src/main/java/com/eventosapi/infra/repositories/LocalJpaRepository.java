package com.eventosapi.infra.repositories;

import com.eventosapi.infra.entities.LocalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LocalJpaRepository extends JpaRepository<LocalEntity, Long>, JpaSpecificationExecutor<LocalEntity> {
}