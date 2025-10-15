# 🐰 SETUP RABBITMQ LOCAL

## ✅ **CONFIGURAÇÃO IMPLEMENTADA:**

### **1. DOCKER COMPOSE ATUALIZADO**
- ✅ Adicionado serviço RabbitMQ com Management UI
- ✅ Configuradas variáveis de ambiente para todos os microsserviços
- ✅ Volume persistente para dados do RabbitMQ

### **2. CONFIGURAÇÕES DE APLICAÇÃO ATUALIZADAS**
- ✅ **inscricao-service**: RabbitMQ local configurado
- ✅ **evento-service**: RabbitMQ local configurado
- ✅ **comunicacoes-service**: RabbitMQ local configurado

---

## 🚀 **COMANDOS PARA EXECUTAR:**

### **PASSO 1: Subir RabbitMQ**
```bash
# Subir apenas o RabbitMQ primeiro
docker compose up -d rabbitmq

# Verificar se está rodando
docker ps | grep rabbitmq

# Aguardar inicialização (30-60 segundos)
docker logs rabbitmq
```

### **PASSO 2: Acessar Management UI**
```bash
# Abrir no browser
http://localhost:15672

# Credenciais:
Usuário: admin
Senha: admin123
```

### **PASSO 3: Verificar Filas**
No Management UI, você deve ver:
- **Queues**: Inicialmente vazio (filas são criadas automaticamente pelos apps)
- **Connections**: Vazio (apps não estão conectados ainda)

### **PASSO 4: Subir todos os serviços**
```bash
# Subir todo o ambiente
docker compose up -d

# OU subir apenas os serviços com mensageria
docker compose up -d rabbitmq postgres evento-api inscricao-api comunicacoes-api

# Verificar logs
docker logs -f evento-api
docker logs -f inscricao-api
docker logs -f comunicacoes-api
```

### **PASSO 5: Executar sem Docker (Local)**
```bash
# Terminal 1 - RabbitMQ (se não usar Docker)
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 \
  -e RABBITMQ_DEFAULT_USER=admin \
  -e RABBITMQ_DEFAULT_PASS=admin123 \
  rabbitmq:3-management-alpine

# Terminal 2 - INSCRICAO-SERVICE
cd inscricao
mvn clean compile
mvn spring-boot:run -DskipTests

# Terminal 3 - COMUNICACOES-SERVICE
cd comunicacoes
mvn clean compile
mvn spring-boot:run -DskipTests

# Terminal 4 - EVENTO-SERVICE
cd evento
mvn clean compile
mvn spring-boot:run -DskipTests
```

---

## 🔍 **VERIFICAÇÕES DE SUCESSO:**

### **1. Logs Esperados no Startup:**

**INSCRICAO-SERVICE:**
```
INFO  - RabbitMQ Host: localhost
INFO  - RabbitMQ connected successfully
INFO  - Queue created: inscricao.criada
INFO  - Started InscricaoApplication
```

**COMUNICACOES-SERVICE:**
```
INFO  - RabbitMQ Host: localhost
INFO  - Queue created: inscricao.criada
INFO  - Queue created: evento.att.comunicacoes
INFO  - RabbitListener containers started
INFO  - Started ComunicacoesApplication
```

**EVENTO-SERVICE:**
```
INFO  - RabbitMQ Host: localhost
INFO  - Queue created: evento.att.comunicacoes
INFO  - Started EventoApplication
```

### **2. Management UI (http://localhost:15672):**

**Queues Tab:**
- ✅ `inscricao.criada` (durável)
- ✅ `evento.att.comunicacoes` (durável)

**Connections Tab:**
- ✅ 3 conexões ativas (uma para cada microsserviço)

---

## 🧪 **TESTE RÁPIDO:**

### **Teste 1: Criar Inscrição**
```bash
# POST http://localhost:8085/api/inscricoes
curl -X POST http://localhost:8085/api/inscricoes \
  -H "Content-Type: application/json" \
  -d '{"eventoId": 1, "usuarioId": 1}'
```

**Verificar no Management UI:**
- Ir em **Queues** → `inscricao.criada`
- Deve aparecer **1 message** (temporariamente)
- Message rate deve mostrar atividade

### **Teste 2: Atualizar Evento**
```bash
# PUT http://localhost:8084/api/eventos/1
curl -X PUT http://localhost:8084/api/eventos/1 \
  -H "Content-Type: application/json" \
  -d '{"titulo": "Evento Teste", "descricao": "Teste"}'
```

---

## 🛠️ **TROUBLESHOOTING:**

### **Problema: RabbitMQ não conecta**
```bash
# Verificar se o container está rodando
docker ps | grep rabbitmq

# Verificar logs
docker logs rabbitmq

# Reiniciar se necessário
docker restart rabbitmq
```

### **Problema: Filas não aparecem**
- Filas são criadas automaticamente quando a aplicação inicia
- Se não aparecem, verificar logs das aplicações
- Verificar se as configurações estão corretas

### **Problema: Aplicação não conecta**
```bash
# Verificar configuração
grep -r "rabbitmq" */src/main/resources/application.properties

# Deve mostrar host localhost nos 3 microsserviços
```

---

## 📊 **CONFIGURAÇÕES FINAIS:**

### **Credenciais RabbitMQ Local:**
- **Host:** localhost
- **Port:** 5672
- **Username:** admin
- **Password:** admin123
- **Virtual Host:** /

### **Filas Configuradas:**
- `inscricao.criada` (nova funcionalidade)
- `evento.att.comunicacoes` (existente)

### **Management UI:**
- **URL:** http://localhost:15672
- **Login:** admin / admin123

---

## 🎉 **VANTAGENS DO SETUP LOCAL:**

✅ **Sem dependência externa** (CloudAMQP removido)
✅ **Desenvolvimento offline**
✅ **Management UI para debug**
✅ **Controle total sobre configurações**
✅ **Dados persistentes** (volume Docker)
✅ **Facilidade para testes**

**Configuração concluída! RabbitMQ rodando localmente! 🚀**