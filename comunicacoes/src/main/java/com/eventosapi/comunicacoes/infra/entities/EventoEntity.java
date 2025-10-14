package com.eventosapi.comunicacoes.infra.entities;

import java.time.LocalDateTime;

import com.eventosapi.comunicacoes.domain.enums.TipoEvento;
import com.eventosapi.comunicacoes.domain.model.Evento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "evento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_evento")
	private Long id;

	@Column(name = "titulo", nullable = false, length = 255)
	private String titulo;

	@Column(name = "descricao", columnDefinition = "TEXT")
	private String descricao;

	@Column(name = "data", nullable = false)
	private LocalDateTime data;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo", nullable = false, length = 255)
	private TipoEvento tipo;

	@Column(name = "max_participantes", nullable = false)
	private Integer maxParticipantes;

	@Column(name = "organizador", nullable = false)
	private Long organizadorId;

	@OneToOne
	@JoinColumn(name = "id_local", nullable = false)
	private LocalEntity local;

	public Evento toDomain() {
		Evento evento = new Evento();
		evento.setId(id);
		evento.setTitulo(titulo);
		evento.setDescricao(descricao);
		evento.setData(data);
		evento.setTipo(tipo);
		evento.setMaxParticipantes(maxParticipantes);
		evento.setOrganizadorId(organizadorId);
		evento.setLocal(local.toDomain());
		return evento;
	}
}
