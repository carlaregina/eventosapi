package com.eventosapi.demo.controller;

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

import com.eventosapi.demo.dtos.FiltroUsuarioDTO;
import com.eventosapi.demo.dtos.UsuarioRequestDTO;
import com.eventosapi.demo.dtos.UsuarioResponseDTO;
import com.eventosapi.demo.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários")
public class UsuarioController {
    
    private final UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Listar todos os usuários com paginação e filtros")
    public Page<UsuarioResponseDTO> listar(FiltroUsuarioDTO filtroUsuarioDTO, Pageable pageable){
        return usuarioService.listarUsuarios(filtroUsuarioDTO, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter usuário por ID")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Usuário encontrado", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = UsuarioResponseDTO.class)) }),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", 
            content = @Content)
    })
    public ResponseEntity<UsuarioResponseDTO> obterPorId(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.obterUsuarioPorId(id));
    }

    @PostMapping
    @Operation(summary = "Criar um novo usuário")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Usuário criado", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = UsuarioResponseDTO.class)) }),
    })
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@RequestBody UsuarioRequestDTO usuarioRequestDTO){
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.cadastrarUsuario(usuarioRequestDTO);
        return ResponseEntity.ok(usuarioResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um usuário existente")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Usuário atualizado", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = UsuarioResponseDTO.class)) }),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", 
            content = @Content)
    })
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable Long id, 
                                                                      @RequestBody UsuarioRequestDTO usuarioRequestDTO){
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.atualizarUsuario(id, usuarioRequestDTO);
        return ResponseEntity.ok(usuarioResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um usuário por ID")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "Usuário apagado", 
            content = { @Content(mediaType = "application/json") }),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", 
            content = @Content)
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}