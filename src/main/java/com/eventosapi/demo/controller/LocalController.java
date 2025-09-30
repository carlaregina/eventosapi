package com.eventosapi.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
