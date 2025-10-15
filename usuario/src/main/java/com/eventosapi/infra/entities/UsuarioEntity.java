package com.eventosapi.infra.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.eventosapi.domain.enums.TipoUsuario;
import com.eventosapi.domain.models.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class UsuarioEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "telefone", nullable = false, length = 20)
    private String telefone;

    @Column(name = "senha", nullable = false, length = 255)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 255)
    private TipoUsuario tipo;

    public Usuario toDomain(){
        return Usuario.builder()
                .id(this.id)
                .nome(this.nome)
                .email(this.email)
                .telefone(this.telefone)
                .senha(this.senha)
                .tipo(this.tipo)
                .build();
    }

    public static UsuarioEntity fromDomain(Usuario usuario){
        UsuarioEntity entity = new UsuarioEntity();
        entity.id = usuario.getId();
        entity.nome = usuario.getNome();
        entity.email = usuario.getEmail();
        entity.telefone = usuario.getTelefone();
        entity.senha = usuario.getSenha();
        entity.tipo = usuario.getTipo();
        
        return entity;
    }

	@Override
	public String getUsername() {
        return this.email;
	}

	@Override
	public String getPassword() {
        return this.senha;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(tipo.toString()));
	}
}