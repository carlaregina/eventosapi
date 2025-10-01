package com.eventosapi.demo.controller;

import com.eventosapi.demo.dtos.InscricaoRequest;
import com.eventosapi.demo.dtos.InscricaoResponse;
import com.eventosapi.demo.services.InscricaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// api/controller/InscricaoController.java
@RestController
@RequestMapping("/api/inscricoes")
@RequiredArgsConstructor
public class InscricaoController {

    private final InscricaoService service;

    @PostMapping
    public ResponseEntity<InscricaoResponse> criar(@RequestBody @Valid InscricaoRequest req) {
        var i = service.criar(req.idEvento(), req.idUsuario());
        return ResponseEntity.status(HttpStatus.CREATED).body(InscricaoResponse.from(i));
    }

    @GetMapping("/{id}")
    public InscricaoResponse buscar(@PathVariable Long id) {
        return InscricaoResponse.from(service.buscar(id));
    }

    @GetMapping
    public List<InscricaoResponse> listar(@RequestParam(required = false) Long eventoId) {
        // ⚠️ Garanta @EntityGraph no repository para carregar evento/usuario (evitar LazyInitializationException)
        return service.listar(eventoId).stream().map(InscricaoResponse::from).toList();
    }

    @PutMapping("/{id}")
    public InscricaoResponse atualizar(@PathVariable Long id, @RequestBody @Valid InscricaoRequest req) {
        if (req.status() == null) throw new IllegalArgumentException("status é obrigatório para atualização");
        return InscricaoResponse.from(service.atualizarStatus(id, req.status()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}