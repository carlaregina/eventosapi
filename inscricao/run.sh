docker# Rodar o serviço de inscrição com o perfil de desenvolvimento
mvn -Dspring-boot.run.profiles=dev spring-boot:run

# buscar por ID (ajuste se o id mudou)
curl -i http://localhost:8081/api/inscricoes/1

# listar paginado
curl -i "http://localhost:8081/api/inscricoes?page=0&size=10"

# atualizar status
curl -i -X PUT http://localhost:8081/api/inscricoes/1 \
  -H "Content-Type: application/json" \
  -d '{"idEvento":1,"idUsuario":2,"status":"CANCELADO"}'

# buscar por evento
curl -G -i http://localhost:8081/api/inscricoes \
  --data-urlencode "idEvento=1" \
  --data-urlencode "page=0" \
  --data-urlencode "size=10"

# buscar por usuario

curl -G -i http://localhost:8081/api/inscricoes \
  --data-urlencode "idUsuario=2" \
  --data-urlencode "page=0" \
  --data-urlencode "size=10"

# buscar por status
curl -G -i http://localhost:8081/api/inscricoes \
  --data-urlencode "status=CONFIRMADA" \
  --data-urlencode "page=0" \
  --data-urlencode "size=10"

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





curl -i -X POST http://localhost:8083/api/locais \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJldmVudG9zIiwic3ViIjoiYWRtaW5AZW1haWwuY29tIiwiZXhwIjoxNzYwMjUzNjUyfQ.gYWR6vo3YTJOkY0g8-UGqMUy_sYJw1_ga9JWA7NrOiI" \
  -d '{
    "nome": "Auditório Central",
    "endereco": "Rua Exemplo, 123",
    "categoria": "INTERNO"
  }'