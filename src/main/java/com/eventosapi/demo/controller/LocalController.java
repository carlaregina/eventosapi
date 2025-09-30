package com.eventosapi.demo.controller;

import static org.springframework.http.HttpStatus.CREATED;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventosapi.demo.dtos.LocalDTO;
import com.eventosapi.demo.models.Local;
import com.eventosapi.demo.services.LocalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/locais")
public class LocalController {

    private final LocalService localService;

    @GetMapping
    public Page<Local> listar(Pageable pageable) {
        return localService.listar(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Local> buscarPorId(@PathVariable Long id) {
        Local local = localService.buscarPorId(id);
        return ResponseEntity.ok(local);
    }

    @PostMapping
    public ResponseEntity<Local> salvar(@RequestBody LocalDTO dto) {
        Local salvo = localService.salvar(dto);
        return ResponseEntity.status(CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Local> atualizar(@PathVariable Long id, @RequestBody LocalDTO dto) {
        Local local = localService.atualizar(id, dto);
        return ResponseEntity.ok(local);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        localService.removerPorId(id);
        return ResponseEntity.noContent().build();
    }
}
