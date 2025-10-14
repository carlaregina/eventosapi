package com.eventosapi.comunicacoes.infra.entities;

import java.time.LocalDateTime;

import com.eventosapi.comunicacoes.domain.enums.StatusInscricao;
import com.eventosapi.comunicacoes.domain.model.Inscricao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inscricao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscricaoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_inscricao")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_evento", nullable = false)
	private EventoEntity evento;

	@ManyToOne
	@JoinColumn(name = "id_usuario", nullable = false)
	private UsuarioEntity usuario;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 255)
	private StatusInscricao status;

	@Column(name = "data", nullable = false)
	private LocalDateTime data;

	public Inscricao toDomain() {
		Inscricao inscricao = new Inscricao();
		inscricao.setId(id);
		inscricao.setEvento(evento.toDomain());
		inscricao.setUsuario(usuario.toDomain());
		inscricao.setData(data);
		inscricao.setStatus(status);
		return  inscricao;
	}
}
