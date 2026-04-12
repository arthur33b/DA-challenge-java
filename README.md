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

### Pré-requisitos
- **Java 21** ou superior instalado
- **Maven 3.6+** instalado
- **Git** para clonar o repositório

### Passos de Instalação

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/arthur33b/DA-challenge-java.git
   cd DA-challenge-java/alura-dashboard
   ```

2. **Compile o projeto:**
   ```bash
   mvn clean compile
   ```

3. **Execute os testes (opcional):**
   ```bash
   mvn test
   ```

4. **Empacote a aplicação:**
   ```bash
   mvn package -DskipTests
   ```

5. **Execute a aplicação:**
   ```bash
   java -jar target/dashboard-1.0.0.jar
   ```

6. **Acesse a aplicação:**
   - **API Base:** http://localhost:8080
   - **Swagger UI:** http://localhost:8080/swagger-ui/index.html
   - **H2 Console:** http://localhost:8080/h2-console

### Configuração do Banco de Dados
- **Banco Ativo:** Oracle Database
- **JDBC URL:** `jdbc:oracle:thin:@//localhost:1521/XE`
- **Usuário:** `rm560820`
- **Senha:** `Fiap25`
- **Dados iniciais:** Carregados automaticamente via `schema.sql` e `data.sql`
- **Nota:** Para usar H2 em memória para desenvolvimento local, descomente as linhas de H2 em `application.properties` e comente as de Oracle

### Funcionalidades Implementadas
- ✅ **Gestão de Cursos** - CRUD completo (apenas leitura implementada)
- ✅ **Categorização** - Organização por áreas de conhecimento
- ✅ **Instrutores** - Perfil dos professores
- ✅ **Estatísticas** - Métricas e relatórios
- ✅ **Autenticação** - Controle de acesso com roles
- ✅ **Documentação** - API documentada automaticamente
- ✅ **Banco H2** - Persistência em memória para desenvolvimento

### Funcionalidades Avançadas (Sprint 3)
- 🔄 **Feign Client** - Chamadas síncronas entre serviços
- 📨 **JMS Messaging** - Mensageria assíncrona (ActiveMQ)
- 🔐 **Spring Security** - Autenticação e autorização robusta
- 📊 **Métricas Avançadas** - Análises estatísticas detalhadas

## 🛠️ Desenvolvimento

### Executar em Modo Desenvolvimento
```bash
# Com recarregamento automático
mvn spring-boot:run

# Ou com debug habilitado
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
```

### Testes
```bash
# Executar todos os testes
mvn test

# Com relatório de cobertura
mvn test jacoco:report
```

### Build para Produção
```bash
# Build otimizado
mvn clean package -Pproduction

# Executar em produção
java -jar -Dspring.profiles.active=production target/dashboard-1.0.0.jar
```

### Configuração Alternativa - H2 (Desenvolvimento Local)
Para usar H2 em memória em vez de Oracle, comente as linhas de Oracle em `application.properties` e descomente as de H2:
```properties
# Oracle configuration (commented out)
# spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/XE
# spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
# spring.datasource.username=rm560820
# spring.datasource.password=Fiap25

# H2 configuration (uncomment to use)
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
```

## 🏗️ Arquitetura e Tecnologias

### Stack Tecnológico
- **Backend:** Java 21 + Spring Boot 3.2.12
- **ORM:** Spring Data JPA + Hibernate
- **Banco:** H2 Database (desenvolvimento) / Oracle (produção)
- **Segurança:** Spring Security + Basic Authentication
- **Documentação:** Swagger/OpenAPI 2.1.0
- **Build:** Maven 3.x
- **Testes:** JUnit 5

### Padrões Implementados
- **RESTful API** - Endpoints seguindo convenções REST
- **MVC** - Separação clara entre Model, View, Controller
- **DTO Pattern** - Transferência de dados entre camadas
- **Service Layer** - Lógica de negócio encapsulada
- **Repository Pattern** - Abstração de acesso a dados
- **Dependency Injection** - Gerenciamento automático de dependências

### Estrutura de Pacotes
```
com.alura.dashboard
├── controller/     # Endpoints REST
├── service/        # Lógica de negócio
├── repository/     # Acesso a dados
├── model/          # Entidades JPA
├── dto/            # Objetos de transferência
├── config/         # Configurações do Spring
├── security/       # Configurações de segurança
├── client/         # Feign clients
├── messaging/      # Componentes JMS
└── DashboardApplication.java
```

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

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

### Padrões de Código
- Use Java 21+ features quando apropriado
- Mantenha cobertura de testes acima de 80%
- Siga convenções do Spring Boot
- Documente métodos públicos com JavaDoc

## 📚 Recursos Adicionais

- **Repositório GitHub:** https://github.com/arthur33b/DA-challenge-java
- **Documentação Spring Boot:** https://spring.io/projects/spring-boot
- **Referência Spring Data JPA:** https://spring.io/projects/spring-data-jpa
- **Documentação H2:** https://h2database.com/html/main.html

## 📄 Licença

Este projeto é parte de um desafio acadêmico/profissional da FIAP.

## 🎯 Status do Projeto

✅ **Sprint 1:** Concluída - Estrutura base e entidades  
✅ **Sprint 2:** Concluída - Endpoints REST e documentação  
✅ **Sprint 3:** Concluída - Segurança, Feign e mensageria  

**Projeto pronto para produção! 🚀**