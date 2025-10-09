package com.eventosapi.evento.infra.entity;

import com.eventosapi.evento.domain.enums.TipoEvento;
import com.eventosapi.evento.domain.model.Evento;
import com.eventosapi.evento.domain.model.Local;
import com.eventosapi.evento.domain.model.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @Column(name = "organizador")
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