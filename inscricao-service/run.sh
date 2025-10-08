# Rodar o serviço de inscrição com o perfil de desenvolvimento
mvn -Dspring-boot.run.profiles=dev spring-boot:run

# buscar por ID (ajuste se o id mudou)
curl -i http://localhost:8081/api/inscricoes/1

# listar paginado
curl -i "http://localhost:8081/api/inscricoes?page=0&size=10"

# atualizar status
curl -i -X PUT http://localhost:8081/api/inscricoes/1 \
  -H "Content-Type: application/json" \
  -d '{"idEvento":1,"idUsuario":2,"status":"CANCELADO"}'

# excluir
curl -i -X DELETE http://localhost:8081/api/inscricoes/1

# 404 - inexistente
curl -i http://localhost:8081/api/inscricoes/9999

# 409/422 - tentar duplicar (mesmo evento/usuario)
curl -i -X POST http://localhost:8081/api/inscricoes \
  -H "Content-Type: application/json" \
  -d '{"idEvento":1,"idUsuario":2,"status":"CONFIRMADA"}'
curl -i -X POST http://localhost:8081/api/inscricoes \
  -H "Content-Type: application/json" \
  -d '{"idEvento":1,"idUsuario":2,"status":"CONFIRMADA"}'



