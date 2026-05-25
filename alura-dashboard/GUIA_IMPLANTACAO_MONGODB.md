# Guia de Implantação - MongoDB para Alura Dashboard

## 📋 Resumo Executivo

Este guia fornece instruções passo-a-passo para implantar e configurar a integração MongoDB da aplicação Alura Dashboard, substituindo a configuração Oracle tradicional.

---

## 🎯 Objetivos

- ✅ Configurar MongoDB localmente ou via Docker
- ✅ Inicializar dados de exemplo (10 cursos)
- ✅ Testar endpoints REST da API MongoDB
- ✅ Exportar e importar dados
- ✅ Monitorar e gerenciar via MongoDB Express

---

## 📦 Pré-requisitos

### Opção 1: MongoDB Local
```bash
# Windows (via Chocolatey)
choco install mongodb-community

# macOS (via Homebrew)
brew install mongodb-community

# Linux (Ubuntu/Debian)
sudo apt-get install -y mongodb-org
```

### Opção 2: Docker (Recomendado)
```bash
# Instalar Docker e Docker Compose
# Windows: https://www.docker.com/products/docker-desktop
# macOS: https://www.docker.com/products/docker-desktop
# Linux: https://docs.docker.com/install/
```

### Ferramentas Auxiliares
```bash
# Instalar MongoDB Client Tools
# Windows: via MongoDB Community
# macOS: brew install mongodb-database-tools
# Linux: sudo apt-get install -y mongodb-tools

# Instalar MongoDB Shell (mongosh)
# Windows: via MongoDB Community
# macOS: brew install mongosh
# Linux: sudo apt-get install -y mongodb-mongosh
```

---

## 🚀 Opção 1: MongoDB Local

### 1.1 Iniciar MongoDB
```bash
# Windows
"C:\Program Files\MongoDB\Server\6.0\bin\mongod.exe" --auth

# macOS
/usr/local/bin/mongod --auth

# Linux
sudo systemctl start mongod
```

### 1.2 Criar usuário admin
```bash
mongosh
use admin
db.createUser({
  user: "admin",
  pwd: "admin123",
  roles: ["root"]
})
exit
```

### 1.3 Conectar como admin
```bash
mongosh "mongodb://admin:admin123@localhost:27017/admin"
```

### 1.4 Criar database e coleção
```bash
use alura_dashboard
db.cursos.drop()

# Executar o script de inicialização
load("mongodb_init.js")
```

---

## 🐳 Opção 2: MongoDB via Docker (Recomendado)

### 2.1 Iniciar containers
```bash
# No diretório do projeto
cd alura-dashboard

# Iniciar MongoDB e MongoDB Express
docker-compose -f docker-compose-mongodb.yml up -d

# Verificar status
docker-compose -f docker-compose-mongodb.yml ps
```

### 2.2 Acessar MongoDB Express
- URL: `http://localhost:8081`
- Usuário: `admin`
- Senha: `admin123`

### 2.3 Executar script de inicialização
```bash
# Conectar ao MongoDB no container
docker exec -it alura-mongodb mongosh -u admin -p admin123 --authenticationDatabase admin

# Dentro do mongosh, executar o script
use alura_dashboard
load("/docker-entrypoint-initdb.d/init.js")

# Ou executar externamente
docker exec alura-mongodb mongosh "mongodb://admin:admin123@localhost:27017/alura_dashboard?authSource=admin" --file ./mongodb_init.js
```

### 2.4 Parar containers
```bash
docker-compose -f docker-compose-mongodb.yml down

# Remover volumes (CUIDADO - deleta dados!)
docker-compose -f docker-compose-mongodb.yml down -v
```

---

## ⚙️ Configurar a Aplicação Spring Boot

### 3.1 Atualizar dependencies (pom.xml)

Adicione estas dependências ao seu `pom.xml`:

```xml
<!-- Spring Data MongoDB -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
    <version>3.2.12</version>
</dependency>

<!-- Lombok (para annotations) - Opcional -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.30</version>
    <scope>provided</scope>
</dependency>

<!-- Opcional: embeddedmongo para testes -->
<dependency>
    <groupId>de.flapdoodle.embed</groupId>
    <artifactId>de.flapdoodle.embed.mongo</artifactId>
    <version>4.13.0</version>
    <scope>test</scope>
</dependency>
```

### 3.2 Ativar MongoDB em application.properties

Edite `src/main/resources/application.properties`:

```properties
# ========== ORACLE (Comentar para usar MongoDB) ==========
# spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/xe
# spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
# spring.datasource.username=your_username
# spring.datasource.password=your_password
# spring.jpa.hibernate.ddl-auto=none
# spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.Oracle12cDialect

# ========== MONGODB (Descomentar para usar MongoDB) ==========
# Local
spring.data.mongodb.uri=mongodb://admin:admin123@localhost:27017/alura_dashboard?authSource=admin

# Docker
# spring.data.mongodb.uri=mongodb://admin:admin123@mongodb:27017/alura_dashboard?authSource=admin

# Cloud (MongoDB Atlas)
# spring.data.mongodb.uri=mongodb+srv://username:password@cluster0.xxxxx.mongodb.net/alura_dashboard?retryWrites=true&w=majority

spring.data.mongodb.database=alura_dashboard
spring.data.mongodb.auto-index-creation=true

# Logging
logging.level.org.springframework.data.mongodb=DEBUG
logging.level.com.mongodb.client=INFO
```

### 3.3 Criar profile específico para MongoDB

Crie `src/main/resources/application-mongodb.yml`:

```yaml
spring:
  data:
    mongodb:
      uri: mongodb://admin:admin123@localhost:27017/alura_dashboard?authSource=admin
      database: alura_dashboard
      auto-index-creation: true
  
logging:
  level:
    org.springframework.data.mongodb: DEBUG
    com.mongodb.client: INFO
```

---

## 🔌 Testar Conectividade

### 4.1 Health Check
```bash
curl http://localhost:8080/actuator/health
```

Resposta esperada:
```json
{
  "status": "UP",
  "components": {
    "mongo": {
      "status": "UP"
    }
  }
}
```

### 4.2 Listar cursos via API
```bash
curl -X GET http://localhost:8080/api/mongodb/cursos \
  -H "Content-Type: application/json"
```

### 4.3 Criar novo curso
```bash
curl -X POST http://localhost:8080/api/mongodb/cursos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Novo Curso Teste",
    "descricao": "Descrição do curso de teste",
    "popularidade": 100,
    "notaMedia": 4.5,
    "ativo": true,
    "matriculados": 50,
    "certificadoDisponivel": true,
    "categoria": {
      "id": "1",
      "nome": "Backend",
      "descricao": "Desenvolvimento backend"
    },
    "instrutor": {
      "id": "1",
      "nome": "Instrutor Teste",
      "email": "instrutor@alura.com",
      "especializacao": "Java",
      "experienciaAnos": 5
    },
    "tags": ["java", "backend"],
    "avaliacoes": [],
    "modulos": []
  }'
```

---

## 📊 Endpoints da API MongoDB

### Buscar todos os cursos
```bash
GET /api/mongodb/cursos
```

### Buscar curso por ID
```bash
GET /api/mongodb/cursos/{id}
```

### Buscar cursos por nome
```bash
GET /api/mongodb/cursos/buscar/nome?nome=java
```

### Buscar cursos por categoria
```bash
GET /api/mongodb/cursos/buscar/categoria?categoriaId=1
```

### Buscar cursos por instructor
```bash
GET /api/mongodb/cursos/buscar/instrutor?instrutorId=1
```

### Buscar cursos por tag
```bash
GET /api/mongodb/cursos/buscar/tags?tag=backend
```

### Top 5 cursos mais populares
```bash
GET /api/mongodb/cursos/top5/popularidade
```

### Top 5 cursos melhor avaliados
```bash
GET /api/mongodb/cursos/top5/nota
```

### Cursos com nota entre X e Y
```bash
GET /api/mongodb/cursos/buscar/nota?notaMinima=4&notaMaxima=5
```

### Estatísticas gerais
```bash
GET /api/mongodb/cursos/estatisticas
```

### Criar novo curso
```bash
POST /api/mongodb/cursos
Content-Type: application/json

{
  "nome": "...",
  "descricao": "...",
  ...
}
```

### Criar múltiplos cursos (batch)
```bash
POST /api/mongodb/cursos/lote
Content-Type: application/json

[
  { "nome": "Curso 1", ... },
  { "nome": "Curso 2", ... }
]
```

### Atualizar curso
```bash
PUT /api/mongodb/cursos/{id}
Content-Type: application/json

{
  "nome": "Nome atualizado",
  ...
}
```

### Adicionar avaliação
```bash
PATCH /api/mongodb/cursos/{id}/avaliacoes
Content-Type: application/json

{
  "usuario": "usuario",
  "nota": 4.5,
  "comentario": "Ótimo curso!"
}
```

### Incrementar popularidade
```bash
PATCH /api/mongodb/cursos/{id}/popularidade?incremento=10
```

### Desativar curso (delete lógico)
```bash
PATCH /api/mongodb/cursos/{id}/desativar
```

### Reativar curso
```bash
PATCH /api/mongodb/cursos/{id}/reativar
```

### Deletar curso (físico)
```bash
DELETE /api/mongodb/cursos/{id}
```

---

## 📤 Exportar Dados

### 5.1 Exportar via script bash
```bash
chmod +x export_mongodb_data.sh
./export_mongodb_data.sh
```

Arquivos gerados:
- `exports/cursos_export_YYYYMMDD_HHMMSS.json` (raw)
- `exports/cursos_formatted_YYYYMMDD_HHMMSS.json` (array)
- `exports/export_summary_YYYYMMDD_HHMMSS.txt` (relatório)

### 5.2 Exportar via MongoDB Express GUI
1. Acesse `http://localhost:8081`
2. Navegue até `alura_dashboard` > `cursos`
3. Clique no ícone de exportação

### 5.3 Exportar via mongosh
```bash
mongosh "mongodb://admin:admin123@localhost:27017/alura_dashboard?authSource=admin" \
  --eval "db.cursos.find().forEach(doc => print(JSON.stringify(doc)))" > cursos_backup.json
```

---

## 📥 Importar Dados

### 6.1 Importar via script bash
```bash
chmod +x import_mongodb_data.sh
./import_mongodb_data.sh exports/cursos_formatted_YYYYMMDD_HHMMSS.json
```

### 6.2 Importar via mongoimport
```bash
mongoimport \
  --uri "mongodb://admin:admin123@localhost:27017/alura_dashboard?authSource=admin" \
  --collection cursos \
  --file cursos_formatted.json \
  --jsonArray \
  --upsert
```

### 6.3 Importar via MongoDB Express GUI
1. Acesse `http://localhost:8081`
2. Clique no ícone de importação
3. Selecione o arquivo JSON

---

## 🔧 Troubleshooting

### Erro: "MongoDB connection refused"
```bash
# Verificar se MongoDB está rodando
# Local
mongosh --eval "db.version()"

# Docker
docker exec alura-mongodb mongosh --eval "db.version()"
```

### Erro: "Authentication failed"
```bash
# Verificar credenciais
# Padrão: admin / admin123
mongosh "mongodb://admin:admin123@localhost:27017/admin"
```

### Coleção vazia
```bash
# Executar script de inicialização
docker exec alura-mongodb mongosh "mongodb://admin:admin123@localhost:27017/alura_dashboard?authSource=admin" --file ./mongodb_init.js

# Ou via mongosh local
mongosh "mongodb://admin:admin123@localhost:27017/alura_dashboard?authSource=admin"
use alura_dashboard
load("mongodb_init.js")
```

### Application não conecta ao MongoDB
1. Verifique `spring.data.mongodb.uri` em `application.properties`
2. Confirme que MongoDB está rodando
3. Teste conexão via `mongosh`
4. Revise logs da aplicação

### Performance lenta
```bash
# Verificar índices
mongosh "mongodb://admin:admin123@localhost:27017/alura_dashboard?authSource=admin"
db.cursos.getIndexes()

# Criar índice faltante
db.cursos.createIndex({ "nome": 1 })
db.cursos.createIndex({ "popularidade": -1 })
```

---

## 📈 Monitoramento

### 7.1 MongoDB Express
- URL: `http://localhost:8081`
- Dashboard visual com estatísticas
- Interface para CRUD de documentos

### 7.2 Spring Boot Actuator
```bash
# Health check
curl http://localhost:8080/actuator/health

# Metrics
curl http://localhost:8080/actuator/metrics
```

### 7.3 Logs
```bash
# Aplicação
tail -f logs/application.log | grep mongodb

# Container MongoDB
docker-compose -f docker-compose-mongodb.yml logs -f mongodb
```

---

## ✅ Checklist de Implantação

- [ ] MongoDB instalado (local ou Docker)
- [ ] Usuário admin criado (admin/admin123)
- [ ] Database alura_dashboard criada
- [ ] Coleção cursos com dados inicializados
- [ ] Spring Boot configurado com MongoDB URI
- [ ] Dependências Maven atualizadas
- [ ] Aplicação iniciada com sucesso
- [ ] Health check retorna UP
- [ ] Endpoints testados via curl/Postman
- [ ] Dados exportados como backup
- [ ] MongoDB Express acessível
- [ ] Logs monitorados

---

## 📞 Suporte

Para problemas ou dúvidas:
1. Consulte os logs da aplicação
2. Verifique conectividade com MongoDB
3. Valide credenciais de autenticação
4. Revise a documentação do Spring Data MongoDB
5. Consulte a documentação oficial do MongoDB

---

## 📚 Referências

- [Spring Data MongoDB Docs](https://spring.io/projects/spring-data-mongodb)
- [MongoDB Manual](https://docs.mongodb.com/manual/)
- [MongoDB Express](https://github.com/mongo-express/mongo-express)
- [Docker MongoDB](https://hub.docker.com/_/mongo)

---

**Data de criação:** 2024  
**Versão:** 1.0  
**Status:** ✅ Completo e testado
