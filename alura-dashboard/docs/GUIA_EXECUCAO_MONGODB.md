# Guia de Execução - Projeto MongoDB Sprint 3

## Passo a Passo para Execução do Projeto

### Opção 1: Execução Completa com Docker (RECOMENDADO)

#### Passo 1: Iniciar MongoDB com Docker

```bash
cd alura-dashboard/alura-dashboard
docker-compose -f docker-compose-mongodb.yml up -d
```

**Aguarde alguns segundos** para o MongoDB inicializar.

#### Passo 2: Verificar se MongoDB está rodando

```bash
docker-compose -f docker-compose-mongodb.yml ps
```

Deve mostrar:
- `alura-mongodb` - running
- `alura-mongo-express` - running

#### Passo 3: Acessar MongoDB Express (Interface Web)

Abra o navegador: **http://localhost:8081**

- **Usuário:** admin
- **Senha:** admin123

#### Passo 4: Compilar a aplicação

```bash
mvn clean package -DskipTests
```

#### Passo 5: Executar a aplicação com profile MongoDB

```bash
java -jar target/dashboard-1.0.0.jar --spring.profiles.active=mongodb
```

Ou no Windows PowerShell:
```powershell
$env:SPRING_PROFILES_ACTIVE="mongodb"
mvn spring-boot:run
```

#### Passo 6: Testar a API

Abra outro terminal e execute:

```bash
# Health check
curl http://localhost:8080/api/mongodb/cursos/health

# Listar todos os cursos
curl http://localhost:8080/api/mongodb/cursos

# Buscar top 5 mais populares
curl http://localhost:8080/api/mongodb/cursos/populares/top5
```

---

### Opção 2: MongoDB Local (sem Docker)

#### Passo 1: Instalar MongoDB localmente

- Windows: https://www.mongodb.com/try/download/community
- Mac: `brew install mongodb-community`
- Linux: Consulte documentação oficial

#### Passo 2: Iniciar MongoDB

```bash
mongod --dbpath /path/to/data
```

#### Passo 3: Executar script de inicialização

```bash
mongosh
use alura_dashboard
load('mongodb_init.js')
```

#### Passo 4: Atualizar application-mongodb.properties

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/alura_dashboard
```

#### Passo 5: Executar a aplicação

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mongodb
```

---

### Opção 3: MongoDB Atlas (Cloud)

#### Passo 1: Criar cluster no MongoDB Atlas

1. Acesse https://www.mongodb.com/atlas
2. Crie uma conta gratuita
3. Crie um cluster M0 (free tier)
4. Configure Network Access (0.0.0.0/0 para testes)
5. Crie um usuário de banco de dados

#### Passo 2: Obter Connection String

```
mongodb+srv://username:password@cluster0.xxxxx.mongodb.net/alura_dashboard?retryWrites=true&w=majority
```

#### Passo 3: Atualizar application-mongodb.properties

```properties
spring.data.mongodb.uri=mongodb+srv://username:password@cluster0.xxxxx.mongodb.net/alura_dashboard?retryWrites=true&w=majority
```

#### Passo 4: Executar a aplicação

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mongodb
```

---

## Testando as Operações CRUD

### CREATE - Criar novo curso

```bash
curl -X POST http://localhost:8080/api/mongodb/cursos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Kubernetes Completo",
    "descricao": "Aprenda K8s do zero ao avançado",
    "popularidade": 500,
    "notaMedia": 4.5,
    "ativo": true,
    "matriculados": 500,
    "certificadoDisponivel": true,
    "categoria": {
      "id": "cat123",
      "nome": "DevOps",
      "descricao": "Infraestrutura"
    },
    "instrutor": {
      "id": "inst123",
      "nome": "João Silva",
      "email": "joao@example.com",
      "especializacao": "Kubernetes",
      "experienciaAnos": 5
    },
    "tags": ["kubernetes", "devops", "containers"]
  }'
```

### READ - Consultar cursos

```bash
# Todos os cursos
curl http://localhost:8080/api/mongodb/cursos

# Por ID
curl http://localhost:8080/api/mongodb/cursos/CURSO_ID

# Por nome
curl http://localhost:8080/api/mongodb/cursos/busca/nome?nome=Java

# Por tag
curl http://localhost:8080/api/mongodb/cursos/busca/tag?tag=backend

# Top 5 populares
curl http://localhost:8080/api/mongodb/cursos/populares/top5

# Estatísticas
curl http://localhost:8080/api/mongodb/cursos/stats/gerais
```

### UPDATE - Atualizar curso

```bash
curl -X PUT http://localhost:8080/api/mongodb/cursos/CURSO_ID \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Java Avançado - ATUALIZADO",
    "popularidade": 3000
  }'
```

### DELETE - Remover curso

```bash
# Delete lógico (desativar)
curl -X DELETE http://localhost:8080/api/mongodb/cursos/CURSO_ID/desativar

# Delete físico
curl -X DELETE http://localhost:8080/api/mongodb/cursos/CURSO_ID
```

---

## Exportar e Importar Dados

### Exportar cursos do MongoDB

```bash
# Linux/Mac
bash export_mongodb_data.sh

# Ou manualmente:
mongoexport --uri="mongodb://admin:admin123@localhost:27017/alura_dashboard?authSource=admin" \
  --collection=cursos \
  --out=cursos_backup.json \
  --jsonArray
```

### Importar cursos para MongoDB

```bash
# Linux/Mac
bash import_mongodb_data.sh

# Ou manualmente:
mongoimport --uri="mongodb://admin:admin123@localhost:27017/alura_dashboard?authSource=admin" \
  --collection=cursos \
  --file=mongodb_documentos.json \
  --jsonArray
```

---

## Acesso à Interface MongoDB Express

**URL:** http://localhost:8081  
**Usuário:** admin  
**Senha:** admin123

Na interface você pode:
- Visualizar todas as coleções
- Executar queries manualmente
- Ver documentos em JSON
- Editar documentos diretamente

---

## Verificação de Logs

```bash
# Logs do MongoDB
docker-compose -f docker-compose-mongodb.yml logs -f mongodb

# Logs da aplicação
# (exibido no terminal onde executou java -jar ou mvn spring-boot:run)
```

---

## Parar os Serviços

```bash
# Parar Docker Compose
docker-compose -f docker-compose-mongodb.yml down

# Parar e remover volumes (CUIDADO: apaga dados!)
docker-compose -f docker-compose-mongodb.yml down -v
```

---

## Resolução de Problemas

### Erro: "Connection refused" ao conectar MongoDB

**Solução:** Verifique se MongoDB está rodando:
```bash
docker-compose -f docker-compose-mongodb.yml ps
```

### Erro: "Authentication failed"

**Solução:** Verifique as credenciais no application-mongodb.properties:
```properties
mongodb://admin:admin123@localhost:27017/alura_dashboard?authSource=admin
```

### Erro: "Port 27017 already in use"

**Solução:** Outro MongoDB está rodando. Pare-o ou mude a porta no docker-compose.yml

### Erro: "Unable to start application"

**Solução:** Verifique se o profile está ativo:
```bash
java -jar target/dashboard-1.0.0.jar --spring.profiles.active=mongodb
```

---

## Swagger UI (Opcional)

Se configurado, acesse a documentação da API:

**URL:** http://localhost:8080/swagger-ui.html

---

## Conclusão

Após seguir este guia, você terá:
- ✅ MongoDB rodando (Docker ou local)
- ✅ 10 documentos inicializados
- ✅ API REST funcionando
- ✅ Operações CRUD testadas
- ✅ Interface web para gerenciar dados

**Pronto para demonstração em vídeo!**
