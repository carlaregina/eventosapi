#!/bin/bash

export TOKEN=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJldmVudG9zIiwic3ViIjoidml0b3IuZW5nZW5oYXJpYXByb0BnbWFpbC5jb20iLCJleHAiOjE3NjA0MTg3NTF9.xjM7BsGAaciBWQnzA8MU53AZa0L06PeYPjpW98JiXr0

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

curl -X POST "http://localhost:8081/api/auth/login" \
    -H "Content-Type: application/json" \
    -d '{
        "email": "vitor.engenhariapro@gmail.com",
        "senha": "123456"
    }' | jq

echo "== Testando criação =="
curl -X POST "http://localhost:8084/api/inscricoes" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer ${TOKEN}" \
    -d '{
        "nome": "Auditório Central",
        "cep": "58000-000",
        "logradouro": "Av. Principal",
        "numero": "100",
        "bairro": "Centro",
        "cidade": "João Pessoa",
        "estado": "PB",
        "tipo": "TEATRO"
    }' | jq

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