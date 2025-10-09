package com.eventosapi.comunicacoes.infra.client;

import com.eventosapi.comunicacoes.application.port.UsuarioClientPort;
import com.eventosapi.comunicacoes.domain.enums.TipoUsuario;
import com.eventosapi.comunicacoes.domain.model.Usuario;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("mock")
public class UsuarioClientMock implements UsuarioClientPort {

    @Override
    public List<Usuario> findAll() {
        Usuario u1 = new Usuario();
        u1.setId(1L);
        u1.setNome("Maria Mock");
        u1.setEmail("maria.mock@email.com");
        u1.setTelefone("999999999");
        u1.setTipo(TipoUsuario.PARTICIPANTE);

        Usuario u2 = new Usuario();
        u2.setId(2L);
        u2.setNome("João Mock");
        u2.setEmail("joao.mock@email.com");
        u2.setTelefone("988888888");
        u2.setTipo(TipoUsuario.STAFF);

        return List.of(u1, u2);
    }

    @Override
    public Usuario findById(Long id) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNome("Usuário Mock " + id);
        usuario.setEmail("mock" + id + "@email.com");
        usuario.setTelefone("999999999");
        usuario.setTipo(TipoUsuario.OUTROS);
        return usuario;
    }
}
