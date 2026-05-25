# Migração do Projeto para MongoDB - Sprint 3

## 1. Justificativa para Escolha do MongoDB

### Por que MongoDB em vez de Oracle (Relacional)?

O MongoDB foi escolhido como banco de dados NoSQL para este projeto por diversas razões significativas:

#### 1.1 Flexibilidade de Schema
- **Schema-less**: Permite adicionar novos atributos sem alterar a estrutura fixa
- **Evolução rápida**: Ideal para projetos em desenvolvimento contínuo
- **Dados variáveis**: Suporta documentos com diferentes estruturas na mesma coleção

#### 1.2 Escalabilidade Horizontal
- **Sharding nativo**: Distribuição de dados entre múltiplos servidores
- **Alta disponibilidade**: Replicação automática com replica sets
- **Crescimento sem limites**: Lidar com volumes massivos de dados

#### 1.3 Performance para Leitura
- **Documentos auto-contidos**: Reduz necessidade de joins complexos
- **Indexação flexível**: Otimização rápida de consultas
- **Cached locality**: Dados relacionados no mesmo documento

#### 1.4 Modelo de Dados Natural
- **Representação JSON/BSON**: Alinhado com aplicações modernas
- **Embedded documents**: Relacionamentos um-para-muitos sem tabelas separadas
- **Arrays de objetos**: Coleções de dados dentro do documento

#### 1.5 Casos de Uso Perfeitos
- **Dashboard e Analytics**: Agregações complexas em tempo real
- **Dados semi-estruturados**: Cursos com variações de atributos
- **Mobile e APIs**: Resposta JSON nativa

---

## 2. Modelo de Dados NoSQL - Estrutura em MongoDB

### 2.1 Desnormalização Estratégica

A migração de modelo relacional para NoSQL envolve **desnormalização**, armazenando dados relacionados no mesmo documento:

```
Modelo Relacional (3 tabelas):
- categoria (id, nome, descricao)
- instrutor (id, nome, email, especializacao)
- curso (id, nome, descricao, categoria_id, instrutor_id, popularidade, notaMedia)

Modelo NoSQL (1 coleção):
- cursos (documento completo com categoria e instrutor embedding)
```

### 2.2 Estrutura de Documento Curso no MongoDB

```json
{
  "_id": ObjectId("507f1f77bcf86cd799439011"),
  "nome": "Java Avançado",
  "descricao": "Aprenda conceitos avançados de Java",
  "popularidade": 1500,
  "notaMedia": 4.8,
  "dataModificacao": ISODate("2024-01-15T10:30:00Z"),
  "ativo": true,
  "categoria": {
    "_id": ObjectId("507f1f77bcf86cd799439012"),
    "nome": "Backend",
    "descricao": "Desenvolvimento voltado para servidor"
  },
  "instrutor": {
    "_id": ObjectId("507f1f77bcf86cd799439013"),
    "nome": "Arthur Borges",
    "email": "arthur@example.com",
    "especializacao": "Java e Spring Boot",
    "experienciaAnos": 5
  },
  "avaliacoes": [
    {"usuario": "joao", "nota": 5, "comentario": "Excelente!", "data": ISODate("2024-01-10T14:22:00Z")},
    {"usuario": "maria", "nota": 4.5, "comentario": "Muito bom", "data": ISODate("2024-01-12T09:15:00Z")}
  ],
  "modulos": [
    {"titulo": "Modulo 1", "duracao": 120, "videoUrl": "https://..."},
    {"titulo": "Modulo 2", "duracao": 90, "videoUrl": "https://..."}
  ],
  "tags": ["java", "backend", "spring", "oop"],
  "matriculados": 1500,
  "certificadoDisponivel": true
}
```

### 2.3 Justificativa das Decisões de Design

| Aspecto | Decisão NoSQL | Justificativa |
|--------|---------------|--------------|
| **Embedding** | Categoria e Instrutor dentro de Curso | Reduz joins; consultas rápidas; dados sempre juntos |
| **Array de Avaliações** | Embedded no documento Curso | Sem tabela separada; crescimento natural; fácil agregação |
| **Array de Módulos** | Embedded no documento Curso | Estrutura hierárquica; sem relacionamentos complexos |
| **_id ObjectId** | Identificador único automático | Native MongoDB; evita auto-increment |
| **Desnormalização** | Dados redundantes (ex: nome instrutor) | Trade-off: mais espaço por menos joins |

### 2.4 Vantagens desta Abordagem

✅ **Uma única consulta** para obter curso completo com categoria e instrutor
✅ **Escalabilidade** sem complexidade de joins distribuídos
✅ **Flexibilidade** para adicionar novo campos sem migrações
✅ **Performance** em leituras (casos de uso principais)
✅ **Simplicidade** de código (menos mapeamento objeto-relacional)

---

## 3. Comparativo: Relacional vs NoSQL

### Consulta em SQL (Oracle)
```sql
SELECT c.*, cat.nome as categoria_nome, i.nome as instrutor_nome
FROM cursos c
LEFT JOIN categoria cat ON c.categoria_id = cat.id
LEFT JOIN instrutor i ON c.instrutor_id = i.id
WHERE c.id = 1;
```

### Consulta em MongoDB
```javascript
db.cursos.findOne({"_id": ObjectId("507f1f77bcf86cd799439011")})
```

**MongoDB é mais simples e rápido para este caso de uso!**

---

## 4. Implementação

### 4.1 Dependências Maven
```xml
<!-- MongoDB Spring Data -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>

<!-- BSON -->
<dependency>
    <groupId>org.mongodb</groupId>
    <artifactId>bson</artifactId>
</dependency>
```

### 4.2 Configuração application.properties
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/alura_dashboard
spring.data.mongodb.database=alura_dashboard
```

### 4.3 Docker Compose para MongoDB
```yaml
version: '3.8'
services:
  mongodb:
    image: mongo:latest
    ports:
      - "27017:27017"
    environment:
      MONGO_INITDB_DATABASE: alura_dashboard
    volumes:
      - mongo_data:/data/db
volumes:
  mongo_data:
```

---

## 5. Próximos Passos

1. ✅ Criar documento Curso MongoDB (entidade)
2. ✅ Implementar repositório MongoDB
3. ✅ Criar 10 documentos JSON/BSON de exemplo
4. ✅ Implementar operações CRUD
5. ✅ Exportar dataset em JSON
6. ✅ Documentação completa

---

## Conclusão

A migração para MongoDB oferece:
- **Flexibilidade** no modelo de dados
- **Performance** em operações de leitura
- **Escalabilidade** horizontal
- **Simplicidade** no código da aplicação

Mantendo todos os requisitos funcionais do projeto original enquanto melhora significativamente a arquitetura.
