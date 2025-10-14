package com.eventosapi.comunicacoes.infra.entities;

import com.eventosapi.comunicacoes.domain.enums.TipoUsuario;
import com.eventosapi.comunicacoes.domain.model.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {

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

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo", length = 255)
	private TipoUsuario tipo;

	public Usuario toDomain() {
		Usuario usuario = new Usuario();
		usuario.setId(id);
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setTelefone(telefone);
		usuario.setTipo(tipo);
		return usuario;
	}
}