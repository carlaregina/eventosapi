#!/bin/bash


export TOKEN=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJldmVudG9zIiwic3ViIjoidml0b3IuZW5nZW5oYXJpYXByb0BnbWFpbC5jb20iLCJleHAiOjE3NjAzMjA3MjN9.S3-AHERCEa5KWrxXBq5R0axBSe8RzNDFHD5ELxMeCUs

curl -X POST "http://localhost:8082/api/usuarios" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer ${TOKEN}" \
    -d '{
        "nome": "Vitor",
        "email": "vitor.engenhariapro@gmail.com",
        "telefone": "99999999",
        "tipo": "OUTROS",
        "senha": "123456"
    }' | jq


    curl -X POST "http://localhost:8082/api/usuarios" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer ${TOKEN}" \
    -d '{
        "nome": "maria",
        "email": "maria@gmail.com",
        "telefone": "99999999",
        "tipo": "PARTICIPANTE",
        "senha": "123456"
    }'

curl -X POST "http://localhost:8081/api/auth/login" \
    -H "Content-Type: application/json" \
    
    -d '{
        "email": "vitor.engenhariapro@gmail.com",
        "senha": "123456"
    }' | jq

echo "== Testando criação  LOCAL=="

export TOKEN=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJldmVudG9zIiwic3ViIjoidml0b3IuZW5nZW5oYXJpYXByb0BnbWFpbC5jb20iLCJleHAiOjE3NjAzMjA3MjN9.S3-AHERCEa5KWrxXBq5R0axBSe8RzNDFHD5ELxMeCUs

curl -X POST http://localhost:8083/api/locais \
  -H "Content-Type: application/json" \
   -H "Authorization: Bearer ${TOKEN}" \
  -d '{
    "nome": "Centro de Eventos",
    "cep": "06401-000",
    "logradouro": "Av. das Nações Unidas",
    "numero": "1000",
    "bairro": "Alphaville",
    "cidade": "Barueri",
    "estado": "SP",
    "tipo": "COMERCIAL"
  }'


echo "== Testando criação EVENTO=="

export TOKEN=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJldmVudG9zIiwic3ViIjoidml0b3IuZW5nZW5oYXJpYXByb0BnbWFpbC5jb20iLCJleHAiOjE3NjAzMjA3MjN9.S3-AHERCEa5KWrxXBq5R0axBSe8RzNDFHD5ELxMeCUs

curl -X POST http://localhost:8084/api/eventos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer ${TOKEN}" \
  -d '{
    "titulo": "Workshop Spring Boot",
    "descricao": "Evento técnico sobre microserviços com Spring Boot",
    "data": "2025-11-10T09:00:00",
    "tipo": "PALESTRA",
    "maxParticipantes": 100,
    "organizador": 1,
    "idLocal": 1
  }'





curl -X POST "http://localhost:8085/api/inscricoes" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer ${TOKEN}" \
   
  -d '{"idEvento":1,"idUsuario":2,"status":"CONFIRMADA"}'

echo "== Testando listagem paginada =="
curl "http://localhost:8084/api/inscricoes?page=0&size=5" -H "Authorization: Bearer ${TOKEN}" | jq

echo "== Testando atualização =="
curl -X PUT "http://localhost:8084/api/inscricoes/1" \
    -H "Authorization: Bearer ${TOKEN}" \
    -H "Content-Type: application/json" \
    -d '{
        "nome": "Auditório Central Attualizado",
        "cep": "58000-000",
        "logradouro": "Av. Principal Attualizado",
        "numero": "100",
        "bairro": "Centro Attualizado",
        "cidade": "João Pessoa Attualizado",
        "estado": "SP",
        "tipo": "PRAIA"
    }' | jq

echo "== Testando busca por ID =="
curl "http://localhost:8084/api/inscricoes/1" -H "Authorization: Bearer ${TOKEN}" | jq

echo "== Testando remoção =="
curl -X DELETE "http://localhost:8084/api/inscricoes/1" -H "Authorization: Bearer ${TOKEN}"

echo "== Testando busca por ID após remoção =="
curl "http://localhost:8084/api/inscricoes/1" -H "Authorization: Bearer ${TOKEN}" | jq