package com.eventosapi.demo.repositories;

import com.eventosapi.demo.models.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LocalRepository extends JpaRepository<Local, Long>, JpaSpecificationExecutor<Local> {

}