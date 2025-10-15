package com.eventosapi.inscricao.infra.entities;

import com.eventosapi.inscricao.domain.enums.TipoUsuario;

import jakarta.persistence.*;
import lombok.*;

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

	public UsuarioEntity(Long id) {
		this.id = id;
	}
}