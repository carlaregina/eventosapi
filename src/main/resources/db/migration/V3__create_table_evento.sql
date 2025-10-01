CREATE TABLE evento (
    id_evento BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    data TIMESTAMP NOT NULL,
    tipo VARCHAR(255) NOT NULL,
    max_participantes INT NOT NULL,
    organizador BIGINT NOT NULL,
    id_local BIGINT NOT NULL,
    CONSTRAINT fk_evento_organizador FOREIGN KEY (organizador) REFERENCES usuario(id_usuario),
    CONSTRAINT fk_evento_local FOREIGN KEY (id_local) REFERENCES local(id_local)
);