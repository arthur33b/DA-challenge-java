# Alura Dashboard

## 📌 Projeto Final - FIAP Java Advanced

## 👥 Integrantes do Grupo

- **Arthur Borges (560820)**  
  Responsável pelo desenvolvimento do projeto na Sprint 2.

- **Ana Eliza (559544)**  
  Responsável pela elaboração dos diagramas.

- **Gustavo Ramos (561055)**  
  Responsável pela Sprint 1.

## 🚀 Como Executar o Projeto

### Pré-requisitos
- Java 21
- Maven 3.9+
- MongoDB (via Docker ou Atlas)

### Opção 1: Com Docker Compose (Recomendado)

```powershell
# 1. Subir MongoDB
docker compose -f docker-compose-mongodb.yml up -d

# 2. Compilar projeto
mvn clean package -DskipTests

# 3. Executar aplicação
java -jar target/dashboard-1.0.0.jar --spring.profiles.active=mongodb

# 4. Acessar
# API: http://localhost:8080/api/mongodb/cursos
# MongoDB Express: http://localhost:8081 (admin/admin123)
```

### Opção 2: Com MongoDB Atlas (Cloud)

1. Criar cluster gratuito em https://www.mongodb.com/cloud/atlas
2. Obter connection string
3. Editar `src/main/resources/application-mongodb.properties`:
```properties
spring.data.mongodb.uri=mongodb+srv://user:pass@cluster.mongodb.net/alura_dashboard
```
4. Executar:
```powershell
mvn clean package -DskipTests
java -jar target/dashboard-1.0.0.jar --spring.profiles.active=mongodb
```

---

## 📖 Documentação

### Arquitetura
- **Backend:** Spring Boot 3.2.12 + Java 21
- **Database:** MongoDB 7.0 (NoSQL)
- **API:** REST com 23 endpoints CRUD
- **Modelo:** Documentos JSON/BSON desnormalizados

### Estrutura do Projeto
```
src/main/java/com/alura/dashboard/
├── config/          # Configurações MongoDB
├── controller/      # Controllers REST (Oracle + MongoDB)
├── model/           # Entidades JPA e Documents MongoDB
├── repository/      # Repositories JPA e MongoDB
└── service/         # Lógica de negócio
```

### Documentação Completa
- 📄 [Projeto MongoDB](docs/PROJETO_MONGODB_SPRINT3.md)
- 📄 [Migração NoSQL](MONGODB_MIGRATION.md)
- 📄 [Guia de Execução](docs/GUIA_EXECUCAO_MONGODB.md)
- 📄 [Checklist](CHECKLIST_SPRINT3.md)

---

## 🗂️ Modelo de Dados MongoDB

### Documento Curso (14 atributos)
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
  "categoria": { "id", "nome", "descricao" },
  "instrutor": { "id", "nome", "email", "especializacao", "experienciaAnos" },
  "tags": ["String"],
  "avaliacoes": [{ "usuario", "nota", "comentario", "data" }],
  "modulos": [{ "titulo", "duracao", "videoUrl", "conteudo" }]
}
```

**Vantagens vs Relacional:**
- ✅ Sem JOINs (dados embedded)
- ✅ Schema flexível
- ✅ Performance em leituras
- ✅ Escalabilidade horizontal
- ✅ JSON nativo

---

## 📚 Documentação Antiga (Gradle/Oracle)

1. Certifique-se de ter o Java 21 instalado em sua máquina.
2. Clone este repositório:
   ```bash
   git clone <URL_DO_REPOSITORIO>
   ```
3. Navegue até o diretório do projeto:
   ```bash
   cd alura-dashboard
   ```
4. Compile e empacote o projeto usando o Gradle:
   ```bash
   ./gradlew build
   ```
   Ou, no Windows:
   ```cmd
   gradlew.bat build
   ```
5. Execute o arquivo JAR gerado:
   ```bash
   java -jar build/libs/alura-dashboard-1.0.0.jar
   ```
6. Acesse a aplicação no navegador em: [http://localhost:8080](http://localhost:8080)

## Documentação da API

### Endpoints

#### Cursos
- **GET /api/cursos**  
  Retorna a lista de cursos.

- **GET /api/cursos/{id}**  
  Retorna os detalhes de um curso específico.

- **POST /api/cursos**  
  Cria um novo curso.  
  **Body:** JSON com os dados do curso.

- **PUT /api/cursos/{id}**  
  Atualiza os dados de um curso existente.  
  **Body:** JSON com os dados atualizados.

- **DELETE /api/cursos/{id}**  
  Remove um curso pelo ID.

### Swagger UI
A documentação completa da API pode ser acessada através do Swagger UI em:  
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)