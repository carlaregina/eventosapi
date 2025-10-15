package com.eventosapi.evento.infra.entity;

import java.time.LocalDateTime;

import com.eventosapi.evento.domain.enums.TipoEvento;
import com.eventosapi.evento.domain.model.Evento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
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

    @Column(name = "id_local", nullable = false)
    private Long localId;


    public static EventoEntity fromDomain(Evento evento) {
        if (evento == null) {
            return null;
        }

        EventoEntity entity = new EventoEntity();
        entity.setId(evento.getId());
        entity.setTitulo(evento.getTitulo());
        entity.setDescricao(evento.getDescricao());
        entity.setData(evento.getData());
        entity.setTipo(evento.getTipo());
        entity.setMaxParticipantes(evento.getMaxParticipantes());
        entity.setOrganizadorId(evento.getOrganizadorId());
         entity.setLocalId(evento.getLocalId());
        return entity;
    }

    public Evento toDomain() {
        Evento evento = new Evento();
        evento.setId(this.id);
        evento.setTitulo(this.titulo);
        evento.setDescricao(this.descricao);
        evento.setData(this.data);
        evento.setTipo(this.tipo);
        evento.setMaxParticipantes(this.maxParticipantes);
         evento.setOrganizadorId(this.organizadorId);
         evento.setLocalId(this.localId);
        return evento;
    }

}