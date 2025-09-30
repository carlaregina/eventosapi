package com.eventosapi.demo.models;

import com.eventosapi.demo.enums.TipoInscricao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "inscricao",
       uniqueConstraints = { @UniqueConstraint(
       columnNames = {"id_evento", "id_usuario"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscricao")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "data", nullable = false)
    private LocalDateTime data = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 255)
    private TipoInscricao tipo;

//    // Getters e Setters
//    public TipoInscricao getTipo() {
//        return tipo;
//    }
//
//    public void setTipo(TipoInscricao tipo) {
//        this.tipo = tipo;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Evento getEvento() {
//        return evento;
//    }
//
//    public void setEvento(Evento evento) {
//        this.evento = evento;
//    }
//
//    public Usuario getUsuario() {
//        return usuario;
//    }
//
//    public void setUsuario(Usuario usuario) {
//        this.usuario = usuario;
//    }
//
//    public LocalDateTime getData() {
//        return data;
//    }
//
//    public void setData(LocalDateTime data) {
//        this.data = data;
//    }
}