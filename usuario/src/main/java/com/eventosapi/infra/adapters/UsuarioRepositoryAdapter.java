package com.eventosapi.infra.adapters;

import com.eventosapi.application.port.UsuarioRepositoryPort;
import org.springframework.stereotype.Repository;
import com.eventosapi.infra.entities.UsuarioEntity;
import com.eventosapi.infra.repositories.UsuarioJpaRepository;
import com.eventosapi.domain.models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import java.util.Optional;
import com.eventosapi.interfaces.dtos.FiltroUsuarioDTO;
import com.eventosapi.interfaces.specification.UsuarioSpecification;

@Repository
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {
    private final UsuarioJpaRepository usuarioJpaRepository;

    public UsuarioRepositoryAdapter(UsuarioJpaRepository usuarioJpaRepository) {
        this.usuarioJpaRepository = usuarioJpaRepository;
    }

    @Override
    public Boolean existeEmail(String email) {
        return usuarioJpaRepository.existsByEmail(email);
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        return usuarioJpaRepository.save(UsuarioEntity.fromDomain(usuario)).toDomain();
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioJpaRepository.findById(id).map(UsuarioEntity::toDomain);
    }

    @Override
    public void deletar(Long id) {
        usuarioJpaRepository.deleteById(id);
    }

    @Override
    public Page<Usuario> buscarTodos(FiltroUsuarioDTO filtro, Pageable pageable) {
        Specification<UsuarioEntity> specification = UsuarioSpecification.build()
            .and(UsuarioSpecification.comNome(filtro.nome()))
            .and(UsuarioSpecification.comEmail(filtro.email()))
            .and(UsuarioSpecification.comTelefone(filtro.telefone()))
            .and(UsuarioSpecification.comTipo(filtro.tipo() != null ? filtro.tipo().name() : null));

        return usuarioJpaRepository.findAll(specification, pageable).map(UsuarioEntity::toDomain);
    }  
}