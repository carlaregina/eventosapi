# eventos-api

Sistema de gerenciamento de eventos.

## Requisitos

- Java 17+
- Maven 3.9+
- PostgreSQL 17

## Como executar

1. Configure o banco de dados PostgreSQL (pode usar o [docker-compose.yml](docker-compose.yml)).
2. Ajuste as variáveis de ambiente no `application.properties` se necessário.
3. Execute o projeto:

```sh
./mvnw spring-boot:run
```