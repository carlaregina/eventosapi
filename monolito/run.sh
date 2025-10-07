#!/bin/bash

# Script para testar endpoints principais da eventosapi

echo "== Testando criação =="
curl -s -X POST "http://localhost:8080/api/inscricoes" \
    -H "Content-Type: application/json" \
    -d '{
        "idEvento": 2,
        "idUsuario": 5,
        "data": "2024-10-01T10:00:00",
        "status": "CONFIRMADA"
    }' | jq

echo "== Testando listagem paginada =="
curl -s "http://localhost:8080/api/inscricoes?page=0&size=5" | jq

echo "== Testando atualização =="
curl -s -X PUT "http://localhost:8080/api/eventos/2" \
    -H "Content-Type: application/json" \
    -d '{
        "titulo": "Workshop de Planejamento de Projetos - Atualizado",
        "descricao": "Aprenda a planejar seus projetos de forma eficaz e eficiente.",
        "data": "2025-11-28T14:30:00",
        "tipo": "CURSO",
        "maxParticipantes": 40,
        "organizadorId": 3,
        "localId": 2
    }' | jq

echo "== Testando busca por ID =="
curl -s "http://localhost:8080/api/inscricoes/1" | jq

echo "== Testando remoção =="
curl -s -X DELETE "http://localhost:8080/api/inscricoes/1"

echo "== Testando busca por ID após remoção =="
curl -s "http://localhost:8080/api/inscricoes/1" | jq