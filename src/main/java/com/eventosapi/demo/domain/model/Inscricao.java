package com.eventosapi.demo.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "inscricao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscricao")
    private Long id;

//    @NotNull(message = "O evento é obrigatório")
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_evento", nullable = false)
//    private Evento evento;
//
//
//    @NotNull(message = "O usuário é obrigatório")
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_usuario", nullable = false)
//    private Usuario usuario;

    @NotNull(message = "A data é obrigatória")
    @Column(nullable = false)
    private LocalDateTime data;
}
