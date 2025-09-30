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
