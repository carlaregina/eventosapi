-- Usuário base
INSERT INTO usuario (id_usuario, nome, email, telefone, tipo)
VALUES (2, 'Carla', 'carla@example.com', '119999999', 'CLIENTE')
ON CONFLICT (id_usuario) DO NOTHING;

INSERT INTO usuario (id_usuario, nome, email, telefone, tipo)
VALUES (2, 'Jose', 'o@example.com', '119999449', 'CLIENTE')
ON CONFLICT (id_usuario) DO NOTHING;


-- Local base
INSERT INTO local (id_local, nome, cep, logradouro, numero, bairro, cidade, estado, tipo)
VALUES (1,'Auditório','00000-000','Rua A','100','Centro','São Paulo','SP','PRESENCIAL')
ON CONFLICT (id_local) DO NOTHING;

-- Evento base (capacidade 3)
INSERT INTO evento (id_evento, titulo, descricao, data, tipo, max_participantes, organizador, id_local)
VALUES (1,'Workshop Java','Desc', NOW() + INTERVAL '7 day','CURSO', 3, 2, 1)
ON CONFLICT (id_evento) DO NOTHING;