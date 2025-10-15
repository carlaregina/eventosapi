# EventosAPI - Sistema de Gerenciamento de Eventos

Sistema de microsserviços para gerenciamento de eventos, inscrições e comunicações, desenvolvido com Spring Boot e arquitetura distribuída.

## 🚀 Como Rodar o Projeto

### Pré-requisitos

- **Docker** e **Docker Compose**
- **Java 17+** (para desenvolvimento local)
- **Maven 3.9+** (para desenvolvimento local)

### Executando com Docker Compose

```bash
# Subir todos os serviços
docker-compose up -d

# Ver logs
docker-compose logs -f [service-name]

# Parar todos os serviços
docker-compose down

# Rebuild e restart
docker-compose up -d --build

# Ver status
docker-compose ps
```

### Executando Localmente (Desenvolvimento)

1. **Inicie as dependências externas:**
```bash
docker-compose up -d postgres rabbitmq mailhog
```

2. **Execute cada serviço individualmente:**
```bash
# Service Discovery (obrigatório primeiro)
cd service-discovery && ./mvnw spring-boot:run

# Auth API
cd auth && ./mvnw spring-boot:run

# Demais serviços (qualquer ordem)
cd usuario && ./mvnw spring-boot:run
cd local && ./mvnw spring-boot:run
cd evento && ./mvnw spring-boot:run
cd inscricao && ./mvnw spring-boot:run
cd comunicacoes && ./mvnw spring-boot:run
cd voucher && ./mvnw spring-boot:run
```

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 17**
- **Spring Boot 3.3.4**
- **Spring Cloud 2023.0.3**
- **Spring Security** (JWT)
- **Spring Data JPA**
- **PostgreSQL**
- **Flyway** (Migração de banco)

### Microsserviços
- **Netflix Eureka** (Service Discovery)
- **Spring Cloud OpenFeign** (Comunicação entre serviços)
- **Spring Cloud Gateway** (API Gateway)

### Mensageria e Comunicação
- **RabbitMQ** (Message Broker)
- **Spring Mail** (Envio de emails)
- **MailHog** (Testing de emails)

### Documentação e Monitoramento
- **SpringDoc OpenAPI 3** (Swagger)
- **Spring Boot Actuator** (Health checks)
- **Grafana + Prometheus + Loki** (Métricas e Logs)

### Relatórios
- **JasperReports** (Geração de PDFs)

### Containerização
- **Docker**
- **Docker Compose**

## 📋 Serviços e Portas

| Serviço | Porta | Descrição |
|---------|-------|-----------|
| **API Gateway** | 8080 | Ponto de entrada único |
| **auth-api** | 8081 | Autenticação e autorização |
| **usuario-api** | 8082 | Gerenciamento de usuários |
| **local-api** | 8083 | Gerenciamento de locais |
| **evento-api** | 8084 | Gerenciamento de eventos |
| **inscricao-api** | 8085 | Gerenciamento de inscrições |
| **comunicacao-api** | 8086 | Envio de emails e notificações |
| **Service Discovery** | 8761 | Eureka Server |

### 📊 Infraestrutura

| Serviço | Portas | Acesso |
|---------|--------|--------|
| **PostgreSQL** | 5432 | Database (postgres/postgres) |
| **RabbitMQ** | 5672, 15672 | http://localhost:15672 (admin/admin) |
| **MailHog** | 1025, 8025 | http://localhost:8025 |
| **Grafana** | 3000 | http://localhost:3000 (admin/gadmin) |

## 🔗 Endpoints Principais

### Documentação API
- **Swagger UI**: `http://localhost:<port>/documentacao.html`
- **Eureka Dashboard**: `http://localhost:8761`