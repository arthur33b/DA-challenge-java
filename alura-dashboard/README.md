# Alura Dashboard

## 3.1 Nome da Aplicação
Alura Dashboard

## 3.2 Integrantes do Grupo

- **Arthur Borges (560820)**  
  Responsável pelo desenvolvimento do projeto na Sprint 2.

- **Ana Eliza (559544)**  
  Responsável pela elaboração dos diagramas.

- **Gustavo Ramos (561055)**  
  Responsável pela Sprint 1.

## 3.3 Instruções para Rodar a Aplicação

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

## Link video proposta
https://drive.google.com/drive/folders/1j1I1V5c2YOP_4ixSUvDUb3kN1gnMXoEO?usp=drive_link