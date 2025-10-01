package com.eventosapi.demo.services;

import com.eventosapi.demo.dtos.UsuarioRequestDTO;
import com.eventosapi.demo.dtos.UsuarioResponseDTO;
import com.eventosapi.demo.models.Usuario;
import com.eventosapi.demo.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public Usuario toEntity(UsuarioRequestDTO usuarioRequestDTO){
        Usuario usuario = Usuario.builder()
            .nome(usuarioRequestDTO.nome())
            .email(usuarioRequestDTO.email())
            .telefone(usuarioRequestDTO.telefone())
            .tipo(usuarioRequestDTO.tipo())
            .build();
        return usuario;
    }

    public UsuarioResponseDTO toDto(Usuario usuario){
        return new UsuarioResponseDTO(
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getTelefone(),
            usuario.getTipo()
        );
    }

    public UsuarioResponseDTO cadastrarUsuario(UsuarioRequestDTO usuarioRequestDTO){
        Usuario usuario = usuarioRepository.save(toEntity(usuarioRequestDTO));

        return toDto(usuario);
    }

    public UsuarioResponseDTO obterUsuarioPorId(Long id){
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));

        return toDto(usuario);
    }

    public void deletarUsuario(Long id){
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));

        usuarioRepository.delete(usuario);
    }

    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioRequestDTO usuarioRequestDTO){
        Usuario usuarioExistente = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));

        usuarioExistente.setNome(usuarioRequestDTO.nome());
        usuarioExistente.setEmail(usuarioRequestDTO.email());
        usuarioExistente.setTelefone(usuarioRequestDTO.telefone());
        usuarioExistente.setTipo(usuarioRequestDTO.tipo());

        Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);

        return toDto(usuarioAtualizado);
    }

    public List<UsuarioResponseDTO> listarUsuarios(){
        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream()
            .map(this::toDto)
            .toList();
    }

}