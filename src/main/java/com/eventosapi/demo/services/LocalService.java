package com.eventosapi.demo.services;

import com.eventosapi.demo.dtos.FiltroLocalDTO;
import com.eventosapi.demo.dtos.LocalDTO;
import com.eventosapi.demo.exceptions.EntidadeNaoEncontradoException;
import com.eventosapi.demo.models.Local;
import com.eventosapi.demo.repositories.LocalRepository;
import com.eventosapi.demo.specifications.LocalSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LocalService {

    private final LocalRepository localRepository;

    @Transactional(readOnly = true)
    public Page<Local> listar(FiltroLocalDTO filtro, Pageable pageable) {
        Specification<Local> specification = LocalSpecification.build()
            .and(LocalSpecification.comNome(filtro.getNome()))
            .and(LocalSpecification.comCep(filtro.getCep()))
            .and(LocalSpecification.comLogradouro(filtro.getLogradouro()))
            .and(LocalSpecification.comNumero(filtro.getNumero()))
            .and(LocalSpecification.comBairro(filtro.getBairro()))
            .and(LocalSpecification.comCidade(filtro.getCidade()))
            .and(LocalSpecification.comEstado(filtro.getEstado()))
            .and(LocalSpecification.comTipo(filtro.getTipo()));

        return localRepository.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    public Local buscarPorId(Long id) {
        return localRepository.findById(id)
            .orElseThrow(() -> new EntidadeNaoEncontradoException("Local não encontrado"));
    }

    @Transactional(rollbackFor = Exception.class)
    public Local salvar(LocalDTO dto) {
        return localRepository.save(toEntity(dto));
    }

    @Transactional(rollbackFor = Exception.class)
    public Local atualizar(Long id, LocalDTO dto) {
        Local local = buscarPorId(id);
        atualizarEntity(local, dto);
        return localRepository.save(local);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removerPorId(Long id) {
        Local local = buscarPorId(id);
        localRepository.delete(local);
    }

    private Local toEntity(LocalDTO dto) {
        Local local = new Local();
        atualizarEntity(local, dto);
        return local;
    }

    private void atualizarEntity(Local local, LocalDTO dto) {
        local.setNome(dto.getNome());
        local.setCep(dto.getCep());
        local.setLogradouro(dto.getLogradouro());
        local.setNumero(dto.getNumero());
        local.setBairro(dto.getBairro());
        local.setCidade(dto.getCidade());
        local.setEstado(dto.getEstado());
        local.setTipo(dto.getTipo());
    }

}