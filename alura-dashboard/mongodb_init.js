// Script MongoDB para inicialização da base de dados Alura Dashboard
// Copie e execute este script no MongoDB Compass ou mongosh

// Usar ou criar o banco de dados
use alura_dashboard;

// Criar coleção de cursos com validação
db.createCollection("cursos", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["nome", "descricao", "popularidade", "notaMedia", "categoria", "instrutor"],
      properties: {
        _id: { bsonType: "objectId" },
        nome: { bsonType: "string", minLength: 5, maxLength: 255 },
        descricao: { bsonType: "string", minLength: 10, maxLength: 1000 },
        popularidade: { bsonType: "int", minimum: 0 },
        notaMedia: { bsonType: "double", minimum: 0, maximum: 5 },
        dataModificacao: { bsonType: "date" },
        ativo: { bsonType: "bool" },
        categoria: { 
          bsonType: "object",
          required: ["id", "nome"],
          properties: {
            id: { bsonType: "string" },
            nome: { bsonType: "string" },
            descricao: { bsonType: "string" }
          }
        },
        instrutor: {
          bsonType: "object",
          required: ["id", "nome", "email"],
          properties: {
            id: { bsonType: "string" },
            nome: { bsonType: "string" },
            email: { bsonType: "string" },
            especializacao: { bsonType: "string" },
            experienciaAnos: { bsonType: "int" }
          }
        },
        avaliacoes: {
          bsonType: "array",
          items: {
            bsonType: "object",
            properties: {
              usuario: { bsonType: "string" },
              nota: { bsonType: "double" },
              comentario: { bsonType: "string" },
              data: { bsonType: "date" }
            }
          }
        },
        modulos: {
          bsonType: "array",
          items: {
            bsonType: "object",
            properties: {
              titulo: { bsonType: "string" },
              duracao: { bsonType: "int" },
              videoUrl: { bsonType: "string" },
              conteudo: { bsonType: "string" }
            }
          }
        },
        tags: {
          bsonType: "array",
          items: { bsonType: "string" }
        },
        matriculados: { bsonType: "int", minimum: 0 },
        certificadoDisponivel: { bsonType: "bool" }
      }
    }
  }
});

// Criar índices para performance
db.cursos.createIndex({ "nome": 1 });
db.cursos.createIndex({ "categoria.id": 1 });
db.cursos.createIndex({ "instrutor.id": 1 });
db.cursos.createIndex({ "popularidade": -1 });
db.cursos.createIndex({ "notaMedia": -1 });
db.cursos.createIndex({ "ativo": 1 });
db.cursos.createIndex({ "tags": 1 });
db.cursos.createIndex({ "dataModificacao": -1 });

// Índice composto para queries comuns
db.cursos.createIndex({ "ativo": 1, "popularidade": -1 });
db.cursos.createIndex({ "categoria.id": 1, "notaMedia": -1 });

print("✓ Coleção 'cursos' criada com sucesso!");
print("✓ Índices criados para otimização de queries!");
print("✓ Schema validação ativada!");

// ==================== INSERIR 10 DOCUMENTOS ====================

db.cursos.insertMany([
  {
    "_id": ObjectId("6507f1f77bcf86cd799439011"),
    "nome": "Java Avançado - Spring Boot Masterclass",
    "descricao": "Aprenda os conceitos avançados de Java com foco em Spring Boot e arquitetura de microserviços",
    "popularidade": 2500,
    "notaMedia": 4.9,
    "dataModificacao": new Date("2024-01-15T10:30:00Z"),
    "ativo": true,
    "matriculados": 2500,
    "certificadoDisponivel": true,
    "categoria": {
      "id": "6507f1f77bcf86cd799439001",
      "nome": "Backend",
      "descricao": "Desenvolvimento voltado para servidor"
    },
    "instrutor": {
      "id": "6507f1f77bcf86cd799439101",
      "nome": "Arthur Borges",
      "email": "arthur.borges@alura.com",
      "especializacao": "Java Enterprise e Spring Framework",
      "experienciaAnos": 10
    },
    "tags": ["java", "spring-boot", "backend", "microserviços", "oop"],
    "avaliacoes": [
      {
        "usuario": "joao_silva",
        "nota": 5.0,
        "comentario": "Excelente curso, muito bem estruturado e didático",
        "data": new Date("2024-01-10T14:22:00Z")
      },
      {
        "usuario": "maria_santos",
        "nota": 4.8,
        "comentario": "Conteúdo avançado, recomendo!",
        "data": new Date("2024-01-12T09:15:00Z")
      }
    ],
    "modulos": [
      {
        "titulo": "Fundamentos de Spring Boot",
        "duracao": 180,
        "videoUrl": "https://videos.alura.com/spring-boot-1",
        "conteudo": "Introdução ao Spring Boot, dependências e configuração"
      },
      {
        "titulo": "Arquitetura de Microserviços",
        "duracao": 240,
        "videoUrl": "https://videos.alura.com/spring-boot-2",
        "conteudo": "Design de microserviços, comunicação entre serviços"
      }
    ]
  },
  {
    "_id": ObjectId("6507f1f77bcf86cd799439012"),
    "nome": "React - Desenvolvimento de Interfaces Modernas",
    "descricao": "Domine React.js e crie interfaces web dinâmicas e responsivas com as melhores práticas",
    "popularidade": 1800,
    "notaMedia": 4.7,
    "dataModificacao": new Date("2024-01-14T14:20:00Z"),
    "ativo": true,
    "matriculados": 1800,
    "certificadoDisponivel": true,
    "categoria": {
      "id": "6507f1f77bcf86cd799439002",
      "nome": "Frontend",
      "descricao": "Desenvolvimento de interfaces web"
    },
    "instrutor": {
      "id": "6507f1f77bcf86cd799439102",
      "nome": "Ana Silva",
      "email": "ana.silva@alura.com",
      "especializacao": "React, Vue.js e Web Design",
      "experienciaAnos": 8
    },
    "tags": ["react", "javascript", "frontend", "web", "ui"],
    "avaliacoes": [
      {
        "usuario": "pedro_web",
        "nota": 4.7,
        "comentario": "Ótimo curso para iniciantes em React",
        "data": new Date("2024-01-08T11:30:00Z")
      }
    ],
    "modulos": [
      {
        "titulo": "Introdução ao React",
        "duracao": 150,
        "videoUrl": "https://videos.alura.com/react-1",
        "conteudo": "JSX, Componentes e Props"
      }
    ]
  },
  {
    "_id": ObjectId("6507f1f77bcf86cd799439013"),
    "nome": "Python para Data Science e Machine Learning",
    "descricao": "Aprenda Python com foco em análise de dados, visualização e algoritmos de machine learning",
    "popularidade": 2100,
    "notaMedia": 4.8,
    "dataModificacao": new Date("2024-01-13T08:45:00Z"),
    "ativo": true,
    "matriculados": 2100,
    "certificadoDisponivel": true,
    "categoria": {
      "id": "6507f1f77bcf86cd799439003",
      "nome": "Data Science",
      "descricao": "Análise de dados e inteligência artificial"
    },
    "instrutor": {
      "id": "6507f1f77bcf86cd799439103",
      "nome": "Dr. Roberto Cunha",
      "email": "roberto.cunha@alura.com",
      "especializacao": "Data Science, IA e Python",
      "experienciaAnos": 12
    },
    "tags": ["python", "data-science", "machine-learning", "pandas", "sklearn"],
    "avaliacoes": [
      {
        "usuario": "cientista_dados",
        "nota": 4.9,
        "comentario": "Melhor curso de Python que já fiz",
        "data": new Date("2024-01-09T15:00:00Z")
      }
    ],
    "modulos": [
      {
        "titulo": "Fundamentos de Python",
        "duracao": 200,
        "videoUrl": "https://videos.alura.com/python-1",
        "conteudo": "Sintaxe, tipos de dados e estruturas"
      }
    ]
  },
  {
    "_id": ObjectId("6507f1f77bcf86cd799439014"),
    "nome": "Docker - Containerização e DevOps",
    "descricao": "Domine Docker para containerizar aplicações e melhorar pipeline de deploy em produção",
    "popularidade": 1400,
    "notaMedia": 4.6,
    "dataModificacao": new Date("2024-01-12T19:15:00Z"),
    "ativo": true,
    "matriculados": 1400,
    "certificadoDisponivel": true,
    "categoria": {
      "id": "6507f1f77bcf86cd799439004",
      "nome": "DevOps",
      "descricao": "Operações e infraestrutura de software"
    },
    "instrutor": {
      "id": "6507f1f77bcf86cd799439104",
      "nome": "Gustavo Ramos",
      "email": "gustavo.ramos@alura.com",
      "especializacao": "Docker, Kubernetes e CI/CD",
      "experienciaAnos": 9
    },
    "tags": ["docker", "devops", "containerização", "kubernetes", "ci-cd"],
    "avaliacoes": [
      {
        "usuario": "devops_pro",
        "nota": 4.8,
        "comentario": "Essencial para qualquer desenvolvedor",
        "data": new Date("2024-01-07T12:00:00Z")
      }
    ],
    "modulos": [
      {
        "titulo": "Introdução ao Docker",
        "duracao": 160,
        "videoUrl": "https://videos.alura.com/docker-1",
        "conteudo": "Imagens, containers e Docker Compose"
      }
    ]
  },
  {
    "_id": ObjectId("6507f1f77bcf86cd799439015"),
    "nome": "SQL Avançado e Otimização de Queries",
    "descricao": "Aprenda SQL avançado com foco em performance, índices e otimização de consultas complexas",
    "popularidade": 1600,
    "notaMedia": 4.5,
    "dataModificacao": new Date("2024-01-11T16:30:00Z"),
    "ativo": true,
    "matriculados": 1600,
    "certificadoDisponivel": true,
    "categoria": {
      "id": "6507f1f77bcf86cd799439005",
      "nome": "Banco de Dados",
      "descricao": "SQL e gestão de dados"
    },
    "instrutor": {
      "id": "6507f1f77bcf86cd799439105",
      "nome": "Fernanda Costa",
      "email": "fernanda.costa@alura.com",
      "especializacao": "SQL, Oracle e PostgreSQL",
      "experienciaAnos": 11
    },
    "tags": ["sql", "database", "performance", "oracle", "postgresql"],
    "avaliacoes": [
      {
        "usuario": "dba_senior",
        "nota": 4.7,
        "comentario": "Excelente para aprofundar conhecimento",
        "data": new Date("2024-01-10T09:45:00Z")
      }
    ],
    "modulos": [
      {
        "titulo": "Índices e Execução de Queries",
        "duracao": 200,
        "videoUrl": "https://videos.alura.com/sql-1",
        "conteudo": "Como otimizar queries com índices"
      }
    ]
  },
  {
    "_id": ObjectId("6507f1f77bcf86cd799439016"),
    "nome": "TypeScript - Type Safety em JavaScript",
    "descricao": "Domine TypeScript e aprenda a escrever código JavaScript seguro e escalável com tipos",
    "popularidade": 1200,
    "notaMedia": 4.4,
    "dataModificacao": new Date("2024-01-10T11:00:00Z"),
    "ativo": true,
    "matriculados": 1200,
    "certificadoDisponivel": true,
    "categoria": {
      "id": "6507f1f77bcf86cd799439002",
      "nome": "Frontend",
      "descricao": "Desenvolvimento de interfaces web"
    },
    "instrutor": {
      "id": "6507f1f77bcf86cd799439106",
      "nome": "Lucas Mendes",
      "email": "lucas.mendes@alura.com",
      "especializacao": "TypeScript, Angular e Node.js",
      "experienciaAnos": 7
    },
    "tags": ["typescript", "javascript", "type-safety", "nodejs", "angular"],
    "avaliacoes": [
      {
        "usuario": "typescript_fan",
        "nota": 4.5,
        "comentario": "Mudou minha forma de programar JavaScript",
        "data": new Date("2024-01-06T14:15:00Z")
      }
    ],
    "modulos": [
      {
        "titulo": "Tipos em TypeScript",
        "duracao": 150,
        "videoUrl": "https://videos.alura.com/typescript-1",
        "conteudo": "Tipos primitivos, interfaces e generics"
      }
    ]
  },
  {
    "_id": ObjectId("6507f1f77bcf86cd799439017"),
    "nome": "AWS - Cloud Computing e Infraestrutura",
    "descricao": "Aprenda a construir, implantar e escalar aplicações na nuvem com Amazon Web Services",
    "popularidade": 1700,
    "notaMedia": 4.6,
    "dataModificacao": new Date("2024-01-09T13:20:00Z"),
    "ativo": true,
    "matriculados": 1700,
    "certificadoDisponivel": true,
    "categoria": {
      "id": "6507f1f77bcf86cd799439004",
      "nome": "DevOps",
      "descricao": "Operações e infraestrutura de software"
    },
    "instrutor": {
      "id": "6507f1f77bcf86cd799439107",
      "nome": "Rafael Santos",
      "email": "rafael.santos@alura.com",
      "especializacao": "AWS, Azure e Google Cloud",
      "experienciaAnos": 10
    },
    "tags": ["aws", "cloud", "ec2", "s3", "lambda"],
    "avaliacoes": [
      {
        "usuario": "cloud_architect",
        "nota": 4.7,
        "comentario": "Abrangente e muito prático",
        "data": new Date("2024-01-08T17:30:00Z")
      }
    ],
    "modulos": [
      {
        "titulo": "Fundamentos da AWS",
        "duracao": 220,
        "videoUrl": "https://videos.alura.com/aws-1",
        "conteudo": "EC2, S3 e RDS"
      }
    ]
  },
  {
    "_id": ObjectId("6507f1f77bcf86cd799439018"),
    "nome": "Git e Controle de Versão - Essencial para Desenvolvimento",
    "descricao": "Domine Git e aprenda as melhores práticas de versionamento de código para trabalho em equipe",
    "popularidade": 900,
    "notaMedia": 4.3,
    "dataModificacao": new Date("2024-01-08T10:45:00Z"),
    "ativo": true,
    "matriculados": 900,
    "certificadoDisponivel": true,
    "categoria": {
      "id": "6507f1f77bcf86cd799439006",
      "nome": "Ferramentas",
      "descricao": "Ferramentas essenciais para desenvolvimento"
    },
    "instrutor": {
      "id": "6507f1f77bcf86cd799439108",
      "nome": "Isabela Moura",
      "email": "isabela.moura@alura.com",
      "especializacao": "Git, GitHub e GitLab",
      "experienciaAnos": 6
    },
    "tags": ["git", "github", "versionamento", "colaboração", "scm"],
    "avaliacoes": [
      {
        "usuario": "junior_dev",
        "nota": 4.4,
        "comentario": "Fundamental para qualquer desenvolvedor",
        "data": new Date("2024-01-05T11:00:00Z")
      }
    ],
    "modulos": [
      {
        "titulo": "Básico de Git",
        "duracao": 120,
        "videoUrl": "https://videos.alura.com/git-1",
        "conteudo": "Commits, branches e merges"
      }
    ]
  },
  {
    "_id": ObjectId("6507f1f77bcf86cd799439019"),
    "nome": "REST APIs - Design e Implementação Profissional",
    "descricao": "Crie APIs REST profissionais seguindo os padrões e melhores práticas da indústria",
    "popularidade": 2000,
    "notaMedia": 4.7,
    "dataModificacao": new Date("2024-01-07T15:50:00Z"),
    "ativo": true,
    "matriculados": 2000,
    "certificadoDisponivel": true,
    "categoria": {
      "id": "6507f1f77bcf86cd799439001",
      "nome": "Backend",
      "descricao": "Desenvolvimento voltado para servidor"
    },
    "instrutor": {
      "id": "6507f1f77bcf86cd799439109",
      "nome": "Mariano Costa",
      "email": "mariano.costa@alura.com",
      "especializacao": "REST APIs, HTTP e Web Services",
      "experienciaAnos": 9
    },
    "tags": ["rest-api", "http", "json", "backend", "web-services"],
    "avaliacoes": [
      {
        "usuario": "api_builder",
        "nota": 4.8,
        "comentario": "Perfeito para construir APIs de qualidade",
        "data": new Date("2024-01-04T12:30:00Z")
      }
    ],
    "modulos": [
      {
        "titulo": "Princípios REST",
        "duracao": 180,
        "videoUrl": "https://videos.alura.com/rest-api-1",
        "conteudo": "HTTP methods, status codes e resources"
      }
    ]
  },
  {
    "_id": ObjectId("6507f1f77bcf86cd799439020"),
    "nome": "Segurança em Aplicações Web - Proteção Contra Vulnerabilidades",
    "descricao": "Aprenda a identificar, prevenir e corrigir vulnerabilidades comuns em aplicações web",
    "popularidade": 1100,
    "notaMedia": 4.5,
    "dataModificacao": new Date("2024-01-06T09:20:00Z"),
    "ativo": true,
    "matriculados": 1100,
    "certificadoDisponivel": true,
    "categoria": {
      "id": "6507f1f77bcf86cd799439007",
      "nome": "Segurança",
      "descricao": "Segurança e proteção de dados"
    },
    "instrutor": {
      "id": "6507f1f77bcf86cd799439110",
      "nome": "Viktor Sokolov",
      "email": "viktor.sokolov@alura.com",
      "especializacao": "Segurança da Informação e Criptografia",
      "experienciaAnos": 14
    },
    "tags": ["segurança", "web-security", "owasp", "criptografia", "autenticação"],
    "avaliacoes": [
      {
        "usuario": "security_expert",
        "nota": 4.6,
        "comentario": "Conteúdo crítico para todo desenvolvedor",
        "data": new Date("2024-01-03T16:45:00Z")
      }
    ],
    "modulos": [
      {
        "titulo": "Vulnerabilidades OWASP Top 10",
        "duracao": 200,
        "videoUrl": "https://videos.alura.com/security-1",
        "conteudo": "SQL Injection, XSS, CSRF e mais"
      }
    ]
  }
]);

print("✓ 10 documentos de cursos inseridos com sucesso!");
print("✓ Total de documentos na coleção: " + db.cursos.countDocuments());
