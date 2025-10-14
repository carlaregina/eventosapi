-- USUARIO (mínimo necessário)
CREATE TABLE IF NOT EXISTS usuario (
  id_usuario BIGSERIAL PRIMARY KEY,
  nome       VARCHAR(255) NOT NULL,
  email      VARCHAR(255) NOT NULL UNIQUE,
  telefone   VARCHAR(20),
  tipo       VARCHAR(255)
);

-- LOCAL (mínimo necessário)
CREATE TABLE IF NOT EXISTS local (
  id_local   BIGSERIAL PRIMARY KEY,
  nome       VARCHAR(255) NOT NULL,
  cep        VARCHAR(9)   NOT NULL,
  logradouro VARCHAR(255) NOT NULL,
  numero     VARCHAR(20)  NOT NULL,
  bairro     VARCHAR(255) NOT NULL,
  cidade     VARCHAR(255) NOT NULL,
  estado     VARCHAR(2)   NOT NULL,
  tipo       VARCHAR(255)
);

-- EVENTO (campos essenciais para capacidade + título)
CREATE TABLE IF NOT EXISTS evento (
  id_evento         BIGSERIAL PRIMARY KEY,
  titulo            VARCHAR(255) NOT NULL,
  descricao         TEXT,
  data              TIMESTAMP    NOT NULL,
  tipo              VARCHAR(255) NOT NULL,
  max_participantes INTEGER      NOT NULL,
  organizador       BIGINT       NOT NULL REFERENCES usuario(id_usuario),
  id_local          BIGINT       NOT NULL REFERENCES local(id_local)
);

-- INSCRICAO (único domínio que vamos manipular aqui)
CREATE TABLE IF NOT EXISTS inscricao (
  id_inscricao BIGSERIAL PRIMARY KEY,
  id_evento    BIGINT NOT NULL REFERENCES evento(id_evento),
  id_usuario   BIGINT NOT NULL REFERENCES usuario(id_usuario),
  data         TIMESTAMP NOT NULL DEFAULT NOW(),
  status       VARCHAR(32) NOT NULL,
  version      INT NOT NULL DEFAULT 0,
  CONSTRAINT uq_inscricao UNIQUE (id_evento, id_usuario)
);

-- Índices úteis
CREATE INDEX IF NOT EXISTS idx_inscricao_evento ON inscricao(id_evento);
CREATE INDEX IF NOT EXISTS idx_inscricao_usuario ON inscricao(id_usuario);