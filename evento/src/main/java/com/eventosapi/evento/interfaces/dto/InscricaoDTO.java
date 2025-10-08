package com.eventosapi.evento.interfaces.dto;

import com.eventosapi.evento.domain.enums.StatusInscricao;
import com.eventosapi.evento.domain.model.Evento;
import com.eventosapi.evento.domain.model.Usuario;

import java.time.LocalDateTime;

public class InscricaoDTO {
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Evento getEvento() {
            return evento;
        }

        public void setEvento(Evento evento) {
            this.evento = evento;
        }

        public Usuario getUsuario() {
            return usuario;
        }

        public void setUsuario(Usuario usuario) {
            this.usuario = usuario;
        }

        public LocalDateTime getData() {
            return data;
        }

        public void setData(LocalDateTime data) {
            this.data = data;
        }

        public StatusInscricao getStatus() {
            return status;
        }

        public void setStatus(StatusInscricao status) {
            this.status = status;
        }

        private Long id;

        private Evento evento;


        private Usuario usuario;


        private LocalDateTime data = LocalDateTime.now();

        private StatusInscricao status;
    }

