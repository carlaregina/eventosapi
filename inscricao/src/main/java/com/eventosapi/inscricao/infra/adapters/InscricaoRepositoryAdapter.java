package com.eventosapi.inscricao.infra.adapters;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.eventosapi.inscricao.application.dtos.FiltroInscricaoDTO;
import com.eventosapi.inscricao.application.port.InscricaoRepositoryPort;
import com.eventosapi.inscricao.domain.enums.StatusInscricao;
import com.eventosapi.inscricao.domain.models.Inscricao;
import com.eventosapi.inscricao.infra.entities.InscricaoEntity;
import com.eventosapi.inscricao.infra.repositories.InscricaoJpaRepository;
import com.eventosapi.inscricao.infra.specifications.InscricaoSpecification;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class InscricaoRepositoryAdapter implements InscricaoRepositoryPort {

	private final InscricaoJpaRepository repository;

	@Override
	public boolean existsByEventoAndUsuario(Long idEvento, Long idUsuario) {
		return repository.existsByEventoIdAndUsuarioId(idEvento, idUsuario);
	}

	@Override
	public long countConfirmadasByEvento(Long idEvento) {
		return repository.countByEventoIdAndStatus(idEvento, StatusInscricao.CONFIRMADA);
	}

	@Override
	public Inscricao save(Inscricao inscricao) {
		var entity = InscricaoEntity.fromDomain(inscricao);
		return repository.save(entity).toDomain();
	}

	@Override
	public Optional<Inscricao> findById(Long id) {
		return repository.findById(id).map(InscricaoEntity::toDomain);
	}

	@Override
	public Page<Inscricao> findAll(FiltroInscricaoDTO filtro, Pageable pageable) {
		Specification<InscricaoEntity> specification = InscricaoSpecification.build()
            .and(InscricaoSpecification.comDataMaiorOuIgualQue(filtro.dataInicio()))
            .and(InscricaoSpecification.comDataMenorOuIgualQue(filtro.dataFim()))
            .and(InscricaoSpecification.comStatus(filtro.status() == null ? null : List.of(filtro.status())))
            .and(InscricaoSpecification.comUsuarioId(filtro.usuarioId()))
            .and(InscricaoSpecification.comEventoId(filtro.eventoId()));
		return repository.findAll(specification, pageable).map(InscricaoEntity::toDomain);
	}
}
