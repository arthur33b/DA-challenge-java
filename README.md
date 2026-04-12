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
4. Compile e empacote o projeto usando o Maven:
   ```bash
   mvn clean package
   ```
5. Execute o arquivo JAR gerado:
   ```bash
   java -jar target/dashboard-1.0.0.jar
   ```
6. Acesse a aplicação no navegador em: [http://localhost:8080](http://localhost:8080)

## Documentação da API

### Autenticação

A aplicação utiliza **Basic Auth** com dois perfis de usuário:

- **admin / password** → ROLE_ADMIN
- **user / password** → ROLE_USER

### Endpoints principais

#### Cursos
- **GET /api/cursos**
  Retorna a lista de todos os cursos.

- **GET /api/cursos/{id}**
  Retorna os detalhes de um curso específico.

- **GET /api/cursos/categoria/{id}**
  Retorna os cursos de uma categoria específica.

- **GET /api/cursos/populares**
  Retorna os cursos mais populares.

- **GET /api/cursos/classificacao/{id}**
  Retorna a classificação do curso com base em popularidade e nota.

#### Estatísticas (ROLE_ADMIN)
- **GET /api/estatisticas/por-categoria**
  Retorna a quantidade de cursos por categoria.

- **GET /api/estatisticas/top5**
  Retorna o top 5 de cursos mais populares.

- **GET /api/estatisticas/avaliacoes**
  Retorna a média de avaliações por categoria.

### Swagger UI
A documentação completa da API pode ser acessada em:
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Link video proposta
https://drive.google.com/drive/folders/1j1I1V5c2YOP_4ixSUvDUb3kN1gnMXoEO?usp=drive_link