package com.eventosapi.demo.repositories;

import com.eventosapi.demo.models.Local;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalRepository extends JpaRepository<Local, Long> {

}