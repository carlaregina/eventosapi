package com.eventosapi.infra.entities;

import java.io.Serializable;
import com.eventosapi.domain.enums.Estado;
import com.eventosapi.domain.enums.TipoLocal;
import com.eventosapi.domain.models.Local;
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

    public Local toDomain(){
        return Local.builder()
                .id(this.id)
                .nome(this.nome)
                .cep(this.cep)
                .logradouro(this.logradouro)
                .numero(this.numero)
                .bairro(this.bairro)
                .cidade(this.cidade)
                .estado(this.estado)
                .tipo(this.tipo).build();
    }

    public static LocalEntity fromDomain(Local local){
        LocalEntity localEntity = new LocalEntity();
        localEntity.id = local.getId();
        localEntity.nome = local.getNome();
        localEntity.cep = local.getCep();
        localEntity.logradouro = local.getLogradouro();
        localEntity.numero = local.getNumero();
        localEntity.bairro = local.getBairro();
        localEntity.cidade = local.getCidade();
        localEntity.estado = local.getEstado();
        localEntity.tipo = local.getTipo();

        return localEntity;
    }
}