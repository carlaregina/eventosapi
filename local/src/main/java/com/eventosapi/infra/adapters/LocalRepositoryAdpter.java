package com.eventosapi.infra.adapters;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.eventosapi.application.port.LocalRepositoryPort;
import com.eventosapi.domain.models.Local;
import com.eventosapi.infra.entities.LocalEntity;
import com.eventosapi.infra.repositories.LocalJpaRepository;
import com.eventosapi.interfaces.dtos.FiltroLocalDTO;
import com.eventosapi.interfaces.specifications.LocalSpecification;

@Repository
public class LocalRepositoryAdpter implements LocalRepositoryPort{
    private final LocalJpaRepository localJpaRepository;

    public LocalRepositoryAdpter(LocalJpaRepository localJpaRepository) {
        this.localJpaRepository = localJpaRepository;
    } 

    @Override
    public Optional<Local> buscarPorId(Long id) {
        return localJpaRepository.findById(id).map(LocalEntity::toDomain);
    }

    @Override
    public Page<Local> listar(FiltroLocalDTO filtro, Pageable pageable) {
        Specification<LocalEntity> specification = LocalSpecification.build()
            .and(LocalSpecification.comNome(filtro.nome()))
            .and(LocalSpecification.comCep(filtro.cep()))
            .and(LocalSpecification.comLogradouro(filtro.logradouro()))
            .and(LocalSpecification.comNumero(filtro.numero()))
            .and(LocalSpecification.comBairro(filtro.bairro()))
            .and(LocalSpecification.comCidade(filtro.cidade()))
            .and(LocalSpecification.comEstado(filtro.estado()))
            .and(LocalSpecification.comTipo(filtro.tipo()));

        return localJpaRepository.findAll(specification, pageable).map(LocalEntity::toDomain);
    }

    @Override
    public Local salvar(Local local) {
        return localJpaRepository.save(LocalEntity.fromDomain(local)).toDomain();
    }

    @Override
    public void deletar(Long id) {
        localJpaRepository.deleteById(id);
    }
}