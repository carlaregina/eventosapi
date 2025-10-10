package com.eventosapi.evento.infra.dtos;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.eventosapi.evento.domain.enums.TipoUsuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO implements UserDetails {

    private String email;
    private TipoUsuario tipo;

	@Override
	public String getUsername() {
        return this.email;
	}

	@Override
	public String getPassword() {
        
        return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(tipo.toString()));
	}
}
