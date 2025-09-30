package com.eventosapi.demo.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventosapi.demo.dtos.LocalDTO;
import com.eventosapi.demo.exceptions.EntidadeNaoEncontradoException;
import com.eventosapi.demo.models.Local;
import com.eventosapi.demo.repositories.LocalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocalService {

    private final LocalRepository localRepository;

    @Transactional(readOnly = true)
    public Page<Local> listar(Pageable pageable) {
        return localRepository.findAll(pageable);
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