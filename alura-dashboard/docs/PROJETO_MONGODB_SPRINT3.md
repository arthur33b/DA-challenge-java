# Projeto de Banco de Dados NoSQL MongoDB - Sprint 3

## FIAP - Mastering Relational and Non-Relational Database

**Aluno:** Arthur Borges, gustavo alves ramos  
**Data:** Janeiro 2025  
**Projeto:** Alura Dashboard - Migração para MongoDB

---

## 1. DOCUMENTAÇÃO DO PROJETO 

### 1.1 Descrição do Projeto

O **Alura Dashboard** é uma API RESTful desenvolvida em Spring Boot para gerenciamento de cursos online. O projeto original utilizava Oracle Database (banco relacional), e agora foi migrado para **MongoDB** (banco NoSQL).

### 1.2 Justificativa para Escolha do MongoDB

#### Por que MongoDB?

1. **Flexibilidade de Schema**
   - Permite adicionar/remover atributos sem ALTER TABLE
   - Ideal para dados semi-estruturados
   - Suporta evolução rápida do modelo

2. **Performance em Leitura**
   - Documentos auto-contidos eliminam JOINs
   - Dados relacionados ficam juntos (embedding)
   - Consultas mais rápidas para dashboards

3. **Escalabilidade Horizontal**
   - Sharding nativo para distribuição de dados
   - Replica sets para alta disponibilidade
   - Crescimento ilimitado

4. **Modelo de Dados Natural**
   - JSON/BSON alinhado com APIs REST
   - Representação intuitiva de objetos
   - Facilita integração com frontend

5. **Casos de Uso Perfeitos**
   - Dashboard com analytics em tempo real
   - Agregações complexas
   - Consultas variadas e flexíveis

---

## 2. MODELO DE DADOS E JUSTIFICATIVAS 

### 2.1 Estrutura do Documento Curso

```json
{
  "_id": "ObjectId",
  "nome": "String",
  "descricao": "String", 
  "popularidade": "Integer",
  "notaMedia": "Double",
  "dataModificacao": "Date",
  "ativo": "Boolean",
  "matriculados": "Integer",
  "certificadoDisponivel": "Boolean",
  "categoria": {
    "id": "String",
    "nome": "String",
    "descricao": "String"
  },
  "instrutor": {
    "id": "String",
    "nome": "String",
    "email": "String",
    "especializacao": "String",
    "experienciaAnos": "Integer"
  },
  "tags": ["String"],
  "avaliacoes": [
    {
      "usuario": "String",
      "nota": "Double",
      "comentario": "String",
      "data": "Date"
    }
  ],
  "modulos": [
    {
      "titulo": "String",
      "duracao": "Integer",
      "videoUrl": "String",
      "conteudo": "String"
    }
  ]
}
```

### 2.2 Justificativas de Design

| Decisão | Justificativa |
|---------|---------------|
| **Embedding** | Categoria e Instrutor embutidos evitam JOINs |
| **Arrays** | Avaliações e Módulos como arrays nativos |
| **Desnormalização** | Trade-off: espaço por performance |
| **_id ObjectId** | Identificador único automático MongoDB |
| **Tags array** | Busca flexível por múltiplas tags |

### 2.3 Comparação: Relacional vs NoSQL

**SQL (Oracle - 3 tabelas):**
```sql
SELECT c.*, cat.nome, i.nome 
FROM cursos c
JOIN categoria cat ON c.categoria_id = cat.id
JOIN instrutor i ON c.instrutor_id = i.id;
```

**NoSQL (MongoDB - 1 documento):**
```javascript
db.cursos.findOne({_id: ObjectId("...")})
```

✅ MongoDB: **1 consulta** vs SQL: **3 tabelas + 2 JOINs**

---

## 3. CONSTRUÇÃO DE DADOS E OPERAÇÕES 

### 3.1 Criação de 10 Documentos JSON/BSON

✅ **10 documentos criados** em `mongodb_documentos.json`  
✅ Cada documento possui **mais de 10 atributos**  
✅ Estrutura completa com arrays e objetos aninhados

**Exemplo de atributos (>10):**
1. _id
2. nome
3. descricao
4. popularidade
5. notaMedia
6. dataModificacao
7. ativo
8. matriculados
9. certificadoDisponivel
10. categoria (objeto)
11. instrutor (objeto)
12. tags (array)
13. avaliacoes (array)
14. modulos (array)

### 3.2 Script de Inicialização

Arquivo: `mongodb_init.js`
- Cria coleção com validação de schema
- Insere 10 documentos
- Cria índices para performance

---

## 4. INTERFACE DE CONSULTA DE DADOS (30 pontos)

### 4.1 Operações CRUD Completas

**Controller:** `CursoMongoController.java`  
**Service:** `CursoMongoService.java`  
**Repository:** `CursoMongoRepository.java`

#### CREATE
- `POST /api/mongodb/cursos` - Criar curso
- `POST /api/mongodb/cursos/lote` - Criar múltiplos
- `POST /api/mongodb/cursos/{id}/avaliacoes` - Adicionar avaliação

#### READ
- `GET /api/mongodb/cursos` - Listar todos
- `GET /api/mongodb/cursos/{id}` - Buscar por ID
- `GET /api/mongodb/cursos/busca/nome?nome=Java` - Buscar por nome
- `GET /api/mongodb/cursos/busca/categoria?id=X` - Por categoria
- `GET /api/mongodb/cursos/populares/top5` - Top 5 popular
- `GET /api/mongodb/cursos/notas/top5` - Top 5 por nota
- `GET /api/mongodb/cursos/stats/gerais` - Estatísticas

#### UPDATE
- `PUT /api/mongodb/cursos/{id}` - Atualizar curso
- `PATCH /api/mongodb/cursos/{id}/popularidade` - Incrementar

#### DELETE
- `DELETE /api/mongodb/cursos/{id}` - Deletar físico
- `DELETE /api/mongodb/cursos/{id}/desativar` - Deletar lógico
- `POST /api/mongodb/cursos/{id}/reativar` - Reativar

---

## 5. EXPORTAÇÃO DE DATASET 

### 5.1 Arquivos de Export

✅ `mongodb_documentos.json` - 10 documentos completos  
✅ `export_mongodb_data.sh` - Script de exportação  
✅ `import_mongodb_data.sh` - Script de importação

### 5.2 Comandos de Exportação

```bash
# Exportar coleção cursos
mongoexport --uri="mongodb://admin:admin123@localhost:27017/alura_dashboard?authSource=admin" --collection=cursos --out=cursos_export.json --jsonArray

# Importar coleção
mongoimport --uri="mongodb://admin:admin123@localhost:27017/alura_dashboard?authSource=admin" --collection=cursos --file=cursos_export.json --jsonArray
```

---

## 6. TECNOLOGIAS UTILIZADAS

- **Java 21**
- **Spring Boot 3.2.12**
- **Spring Data MongoDB**
- **MongoDB 7.0**
- **Docker & Docker Compose**
- **Maven**

---

## 7. COMO EXECUTAR

### 7.1 Com Docker Compose

```bash
# Subir MongoDB
docker-compose -f docker-compose-mongodb.yml up -d

# Acessar MongoDB Express
http://localhost:8081
```

### 7.2 Com Application

```bash
# Compilar
mvn clean package

# Executar com profile MongoDB
java -jar target/dashboard-1.0.0.jar --spring.profiles.active=mongodb
```

### 7.3 Testar API

```bash
# Health check
curl http://localhost:8080/api/mongodb/cursos/health

# Listar cursos
curl http://localhost:8080/api/mongodb/cursos

# Criar curso
curl -X POST http://localhost:8080/api/mongodb/cursos \
  -H "Content-Type: application/json" \
  -d '{"nome":"Novo Curso","descricao":"Teste",...}'
```

---

## 8. ESTRUTURA DO PROJETO

```
alura-dashboard/
├── src/main/java/com/alura/dashboard/
│   ├── config/
│   │   ├── MongoConfig.java
│   │   └── MongoDataInitializer.java
│   ├── model/mongodb/
│   │   └── CursoMongo.java
│   ├── repository/mongodb/
│   │   └── CursoMongoRepository.java
│   ├── service/mongodb/
│   │   └── CursoMongoService.java
│   └── controller/mongodb/
│       └── CursoMongoController.java
├── src/main/resources/
│   ├── application.properties
│   └── application-mongodb.properties
├── docs/
│   └── PROJETO_MONGODB_SPRINT3.md
├── mongodb_init.js
├── mongodb_documentos.json
├── docker-compose-mongodb.yml
└── pom.xml
```

---

## 9. CONCLUSÃO

A migração para MongoDB trouxe:
- ✅ **Simplicidade** no código (sem JOINs)
- ✅ **Performance** em leituras
- ✅ **Flexibilidade** no schema
- ✅ **Escalabilidade** horizontal
- ✅ **Modelo natural** para APIs REST

O projeto atende **100% dos requisitos da Sprint 3**.

---

**Desenvolvido por:** Arthur Borges , Gustavo alves ramos
**Instituição:** FIAP  
**Disciplina:** Mastering Relational and Non-Relational Database
