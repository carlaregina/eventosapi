CREATE TABLE usuario (
    id_usuario BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    tipo VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE local (
    id_local BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cep VARCHAR(9) NOT NULL,
    logradouro VARCHAR(255) NOT NULL,
    numero VARCHAR(20) NOT NULL,
    bairro VARCHAR(255) NOT NULL,
    cidade VARCHAR(255) NOT NULL,
    estado VARCHAR(2) NOT NULL,
    tipo VARCHAR(255)
);

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