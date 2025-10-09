package com.eventosapi.application.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eventosapi.application.exceptions.EntidadeNaoEncontradoException;
import com.eventosapi.application.port.LocalRepositoryPort;
import com.eventosapi.domain.models.Local;
import com.eventosapi.interfaces.dtos.FiltroLocalDTO;
import com.eventosapi.interfaces.dtos.LocalRequestDTO;
import com.eventosapi.interfaces.dtos.LocalResponseDTO;

@Service
public class LocalService { 
    private final LocalRepositoryPort localRepositoryPort;

    public LocalService(LocalRepositoryPort localRepositoryPort) {
        this.localRepositoryPort = localRepositoryPort;
    }

    public Page<Local> buscarTodosLocais(FiltroLocalDTO filtroLocalDTO, Pageable pageable){
        return localRepositoryPort.listar(filtroLocalDTO, pageable);
    }

    public Local atualizarLocal(Long id, Local local){
        Local localExistente = localRepositoryPort.buscarPorId(id)
            .orElseThrow(() -> new EntidadeNaoEncontradoException("Local não encontrado com ID: " + id));

        localExistente.setNome(local.getNome());
        localExistente.setCep(local.getCep());
        localExistente.setLogradouro(local.getLogradouro());
        localExistente.setNumero(local.getNumero());
        localExistente.setBairro(local.getBairro());
        localExistente.setCidade(local.getCidade());
        localExistente.setEstado(local.getEstado());
        localExistente.setTipo(local.getTipo());

        return localRepositoryPort.salvar(localExistente);
    }

    public Local obterLocalPorId(Long id){
        return localRepositoryPort.buscarPorId(id)
            .orElseThrow(() -> new EntidadeNaoEncontradoException("Local não encontrado com ID: " + id));
    }

    public Local cadastrarLocal(Local local){
        return localRepositoryPort.salvar(local);
    }

    public void deletarLocal(Long id){
        if(!localRepositoryPort.buscarPorId(id).isPresent()){
            throw new EntidadeNaoEncontradoException("Local não encontrado com ID: " + id);
        }
        localRepositoryPort.deletar(id);
    }

    public static LocalResponseDTO toResponseDTO(Local local) {
        return new LocalResponseDTO(
            local.getNome(),
            local.getCep(),
            local.getLogradouro(),
            local.getNumero(),
            local.getBairro(),
            local.getCidade(),
            local.getEstado(),
            local.getTipo()
        );
    }

    public static Local fromRequestDTO(LocalRequestDTO dto) {
        return Local.builder()
            .nome(dto.nome())
            .cep(dto.cep())
            .logradouro(dto.logradouro())
            .numero(dto.numero())
            .bairro(dto.bairro())
            .cidade(dto.cidade())
            .estado(dto.estado())
            .tipo(dto.tipo())
            .build();
    }
}