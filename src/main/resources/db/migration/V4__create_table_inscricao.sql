CREATE TABLE inscricao (
    id_inscricao BIGSERIAL PRIMARY KEY,
    id_evento BIGINT NOT NULL,
    id_usuario BIGINT NOT NULL,
    data TIMESTAMP NOT NULL DEFAULT NOW(),
    status VARCHAR(255) NOT NULL,
    CONSTRAINT fk_inscricao_evento FOREIGN KEY (id_evento) REFERENCES evento(id_evento),
    CONSTRAINT fk_inscricao_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    CONSTRAINT uq_inscricao UNIQUE (id_evento, id_usuario)
);
