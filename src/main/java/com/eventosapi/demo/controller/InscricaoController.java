package com.eventosapi.demo.controller;


import com.eventosapi.demo.dtos.InscricaoRequestDTO;
import com.eventosapi.demo.dtos.InscricaoResponseDTO;
import com.eventosapi.demo.exceptions.DuplicidadeInscricaoException;
import com.eventosapi.demo.exceptions.RecursoNaoEncontradoException;
import com.eventosapi.demo.models.Inscricao;
import com.eventosapi.demo.services.InscricaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


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
    public List<InscricaoResponseDTO> listar(@RequestParam(required = false) Long eventoId) {
        return service.listar(eventoId).stream().map(InscricaoResponseDTO::from).toList();
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