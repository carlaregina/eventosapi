package com.eventosapi.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eventosapi.demo.dtos.InscricaoRequestDTO;
import com.eventosapi.demo.dtos.InscricaoResponseDTO;
import com.eventosapi.demo.services.InscricaoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inscricoes")
@RequiredArgsConstructor
public class InscricaoController {

    private final InscricaoService service;

    @PostMapping
    public ResponseEntity<InscricaoResponseDTO> criar(@RequestBody @Valid InscricaoRequestDTO req) {
        var i = service.criar(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(InscricaoResponseDTO.from(i));
    }

    @GetMapping("/{id}")
    public InscricaoResponseDTO buscar(@PathVariable Long id) {
        return InscricaoResponseDTO.from(service.buscar(id));
    }

    @GetMapping
    public ResponseEntity<Page<InscricaoResponseDTO>> listar(Pageable pageable) {
        Page<InscricaoResponseDTO> page = service.listar(pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    public InscricaoResponseDTO atualizar(@PathVariable Long id, @RequestBody @Valid InscricaoRequestDTO req) {
        if (req.status() == null) throw new IllegalArgumentException("status é obrigatório para atualização");
        return InscricaoResponseDTO.from(service.atualizarStatus(id, req.status()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}