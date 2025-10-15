package com.eventosapi.comunicacoes.infra.entities;

import java.io.Serializable;

import com.eventosapi.comunicacoes.domain.enums.Estado;
import com.eventosapi.comunicacoes.domain.enums.TipoLocal;
import com.eventosapi.comunicacoes.domain.model.Local;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "local")
public class LocalEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_local")
    private Long id;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "cep", nullable = false, length = 9)
    private String cep;

    @Column(name = "logradouro", nullable = false, length = 255)
    private String logradouro;

    @Column(name = "numero", nullable = false, length = 20)
    private String numero;

    @Column(name = "bairro", nullable = false, length = 255)
    private String bairro;

    @Column(name = "cidade", nullable = false, length = 255)
    private String cidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 2)
    private Estado estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 255)
    private TipoLocal tipo;

    public Local toDomain() {
        Local local = new Local();
        local.setId(this.id);
        local.setNome(this.nome);
        local.setCep(this.cep);
        local.setLogradouro(this.logradouro);
        local.setNumero(this.numero);
        local.setBairro(this.bairro);
        local.setCidade(this.cidade);
        local.setEstado(this.estado);
        local.setTipo(this.tipo);
        return local;
    }
}