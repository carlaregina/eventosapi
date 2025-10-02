package com.eventosapi.demo.services;

import com.eventosapi.demo.dtos.FiltroUsuarioDTO;
import com.eventosapi.demo.dtos.UsuarioRequestDTO;
import com.eventosapi.demo.dtos.UsuarioResponseDTO;
import com.eventosapi.demo.exceptions.DuplicidadeEmailUsuarioException;
import com.eventosapi.demo.exceptions.EntidadeNaoEncontradoException;
import com.eventosapi.demo.models.Usuario;
import com.eventosapi.demo.repositories.UsuarioRepository;
import com.eventosapi.demo.specifications.UsuarioSpecification;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
        if(usuarioRepository.existsByEmail(usuarioRequestDTO.email())){
            throw new DuplicidadeEmailUsuarioException("Já existe um usuário cadastrado com o email: " + usuarioRequestDTO.email());
        }
        Usuario usuario = usuarioRepository.save(toEntity(usuarioRequestDTO));

        return toDto(usuario);
    }

    public UsuarioResponseDTO obterUsuarioPorId(Long id){
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new EntidadeNaoEncontradoException("Usuário não encontrado com ID: " + id));

        return toDto(usuario);
    }

    public void deletarUsuario(Long id){
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new EntidadeNaoEncontradoException("Usuário não encontrado com ID: " + id));

        usuarioRepository.delete(usuario);
    }

    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioRequestDTO usuarioRequestDTO){
        Usuario usuarioExistente = usuarioRepository.findById(id)
            .orElseThrow(() -> new EntidadeNaoEncontradoException("Usuário não encontrado com ID: " + id));

        usuarioExistente.setNome(usuarioRequestDTO.nome());
        usuarioExistente.setEmail(usuarioRequestDTO.email());
        usuarioExistente.setTelefone(usuarioRequestDTO.telefone());
        usuarioExistente.setTipo(usuarioRequestDTO.tipo());

        Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);

        return toDto(usuarioAtualizado);
    }

    @Transactional(readOnly = true)
    public Page<UsuarioResponseDTO> listarUsuarios(FiltroUsuarioDTO filtro, Pageable pageable) {
        Specification<Usuario> specification = UsuarioSpecification.build()
            .and(UsuarioSpecification.comNome(filtro.getNome()))
            .and(UsuarioSpecification.comEmail(filtro.getEmail()))
            .and(UsuarioSpecification.comTelefone(filtro.getTelefone()))
            .and(UsuarioSpecification.comTipo(filtro.getTipo() != null ? filtro.getTipo().name() : null));

        return usuarioRepository.findAll(specification, pageable).map(this::toDto);
    }

}