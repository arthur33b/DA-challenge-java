# ✅ Checklist Sprint 3 - MongoDB

## Requisitos da Sprint 3

### 📄 1. Documentação do Projeto (15 pontos)

- [x] **Descrição detalhada do projeto**
  - Arquivo: `docs/PROJETO_MONGODB_SPRINT3.md`
  - Seção 1: Descrição completa

- [x] **Justificativa para escolha do MongoDB**
  - Arquivo: `docs/PROJETO_MONGODB_SPRINT3.md`
  - Seção 1.2: 5 justificativas técnicas
  - Arquivo: `MONGODB_MIGRATION.md` - complementar

### 📊 2. Modelo de Dados e Justificativas (15 pontos)

- [x] **Descrição dos modelos de dados**
  - Arquivo: `docs/PROJETO_MONGODB_SPRINT3.md`
  - Seção 2: Estrutura completa do documento

- [x] **Explicação de como atendem os requisitos**
  - Tabela de justificativas de design
  - Comparação Relacional vs NoSQL
  - Embedding vs Referencing

- [x] **Código do modelo implementado**
  - `src/main/java/com/alura/dashboard/model/mongodb/CursoMongo.java`
  - Classes internas: Categoria, Instrutor, Avaliação, Módulo

### 🏗️ 3. Construção de Dados e Operações (20 pontos)

- [x] **10 documentos JSON/BSON criados**
  - Arquivo: `mongodb_documentos.json`
  - Cada documento com MAIS de 10 atributos ✓

- [x] **Atributos por documento:**
  1. ✅ _id (ObjectId)
  2. ✅ nome (String)
  3. ✅ descricao (String)
  4. ✅ popularidade (Integer)
  5. ✅ notaMedia (Double)
  6. ✅ dataModificacao (Date)
  7. ✅ ativo (Boolean)
  8. ✅ matriculados (Integer)
  9. ✅ certificadoDisponivel (Boolean)
  10. ✅ categoria (Object com id, nome, descricao)
  11. ✅ instrutor (Object com id, nome, email, especializacao, experienciaAnos)
  12. ✅ tags (Array de Strings)
  13. ✅ avaliacoes (Array de Objects)
  14. ✅ modulos (Array de Objects)

- [x] **Script de inicialização**
  - `mongodb_init.js` - Cria coleção, índices e insere dados

### 🖥️ 4. Interface de Consulta de Dados (30 pontos)

#### CREATE Operations
- [x] `POST /api/mongodb/cursos` - Criar curso
- [x] `POST /api/mongodb/cursos/lote` - Criar múltiplos
- [x] `POST /api/mongodb/cursos/{id}/avaliacoes` - Adicionar avaliação

#### READ Operations
- [x] `GET /api/mongodb/cursos` - Listar todos
- [x] `GET /api/mongodb/cursos/{id}` - Buscar por ID
- [x] `GET /api/mongodb/cursos/busca/nome?nome=X` - Buscar por nome
- [x] `GET /api/mongodb/cursos/busca/categoria?id=X` - Por categoria
- [x] `GET /api/mongodb/cursos/busca/instrutor?id=X` - Por instrutor
- [x] `GET /api/mongodb/cursos/busca/tag?tag=X` - Por tag
- [x] `GET /api/mongodb/cursos/populares/top5` - Top 5 popular
- [x] `GET /api/mongodb/cursos/notas/top5` - Top 5 por nota
- [x] `GET /api/mongodb/cursos/populares?min=X` - Por popularidade mínima
- [x] `GET /api/mongodb/cursos/notas?min=X` - Por nota mínima
- [x] `GET /api/mongodb/cursos/ativos` - Apenas ativos
- [x] `GET /api/mongodb/cursos/stats/gerais` - Estatísticas
- [x] `GET /api/mongodb/cursos/health` - Health check

#### UPDATE Operations
- [x] `PUT /api/mongodb/cursos/{id}` - Atualizar curso
- [x] `PATCH /api/mongodb/cursos/{id}/popularidade` - Incrementar popularidade

#### DELETE Operations
- [x] `DELETE /api/mongodb/cursos/{id}` - Deletar físico
- [x] `DELETE /api/mongodb/cursos/{id}/desativar` - Deletar lógico
- [x] `POST /api/mongodb/cursos/{id}/reativar` - Reativar
- [x] `DELETE /api/mongodb/cursos/lote` - Deletar múltiplos

### 📦 5. Exportação de Dataset (20 pontos)

- [x] **Dataset exportado**
  - Arquivo: `mongodb_documentos.json`
  - 10 documentos completos em JSON

- [x] **Scripts de exportação**
  - `export_mongodb_data.sh` - Bash script
  - Comando mongoexport documentado

- [x] **Scripts de importação**
  - `import_mongodb_data.sh` - Bash script
  - Comando mongoimport documentado

- [x] **Demonstração da estrutura**
  - JSON formatado e legível
  - Todos os campos visíveis

### 🎥 6. Vídeo com Áudio (15 pontos)

- [ ] **Vídeo gravado** (PENDENTE)
  - Roteiro pronto: `docs/ROTEIRO_VIDEO.md`
  - Duração sugerida: 12-15 minutos
  - Resolução mínima: 720p

- [ ] **Áudio presente e claro**
  - Explicação de cada etapa
  - Sem ruídos de fundo

- [ ] **Demonstração do funcionamento**
  - Iniciar MongoDB
  - Executar aplicação
  - Mostrar CRUD operations
  - Exportar dados

- [ ] **Explicação da codificação**
  - Modelo de dados
  - Repository
  - Service
  - Controller

---

## 🗂️ Arquivos Entregáveis

### Documentação (PDF)
- [ ] `docs/PROJETO_MONGODB_SPRINT3.md` → Converter para PDF
- [ ] `MONGODB_MIGRATION.md` → Anexar ao PDF
- [ ] `docs/GUIA_EXECUCAO_MONGODB.md` → Anexar ao PDF

### Código
- [x] `src/main/java/com/alura/dashboard/model/mongodb/CursoMongo.java`
- [x] `src/main/java/com/alura/dashboard/repository/mongodb/CursoMongoRepository.java`
- [x] `src/main/java/com/alura/dashboard/service/mongodb/CursoMongoService.java`
- [x] `src/main/java/com/alura/dashboard/controller/mongodb/CursoMongoController.java`
- [x] `src/main/java/com/alura/dashboard/config/MongoConfig.java`
- [x] `src/main/java/com/alura/dashboard/config/MongoDataInitializer.java`

### Scripts
- [x] `mongodb_init.js` - Inicialização do banco
- [x] `mongodb_documentos.json` - 10 documentos
- [x] `docker-compose-mongodb.yml` - Setup Docker
- [x] `export_mongodb_data.sh` - Exportação
- [x] `import_mongodb_data.sh` - Importação
- [x] `test_crud_mongodb.sh` - Teste Bash
- [x] `test_crud_mongodb.ps1` - Teste PowerShell

### Configuração
- [x] `pom.xml` - Dependências MongoDB
- [x] `src/main/resources/application-mongodb.properties`

### Vídeo
- [ ] `video_sprint3_mongodb.mp4` (ou link do YouTube/Drive)

---

## 🚀 Antes de Entregar

### Testes Finais

1. **Teste Docker**
```bash
docker-compose -f docker-compose-mongodb.yml down -v
docker-compose -f docker-compose-mongodb.yml up -d
# Aguardar 10 segundos
```

2. **Teste Aplicação**
```bash
mvn clean package
java -jar target/dashboard-1.0.0.jar --spring.profiles.active=mongodb
# Aguardar inicialização
```

3. **Teste CRUD**
```bash
# Linux/Mac
bash test_crud_mongodb.sh

# Windows
.\test_crud_mongodb.ps1
```

4. **Verificar MongoDB Express**
- Acessar: http://localhost:8081
- Verificar 10 documentos na coleção cursos

5. **Teste Export**
```bash
bash export_mongodb_data.sh
# Verificar arquivo gerado
```

### Revisão de Documentação

- [ ] Revisar ortografia e gramática
- [ ] Verificar links e referências
- [ ] Garantir formatação consistente
- [ ] Confirmar que todos os requisitos foram atendidos

### Preparação do Vídeo

- [ ] Testar todos os comandos
- [ ] Preparar ambiente (fechar abas, limpar terminal)
- [ ] Revisar roteiro
- [ ] Testar microfone
- [ ] Fazer gravação de teste (2 min)

---

## 📊 Pontuação Esperada

| Item | Pontos | Status |
|------|--------|--------|
| Documentação do Projeto | 15 | ✅ Completo |
| Modelo de Dados e Justificativas | 15 | ✅ Completo |
| Construção de Dados e Operações | 20 | ✅ Completo |
| Interface de Consulta (CRUD) | 30 | ✅ Completo |
| Exportação de Dataset | 20 | ✅ Completo |
| Vídeo com Áudio | 15 | ⏳ Pendente |
| **TOTAL** | **115** | **100/115** |

---

## ⚠️ Penalidades a Evitar

- ❌ **Falta de documentação** → Perda de 70% da nota
- ❌ **Requisitos não cumpridos** → Perda de 50% do item
- ❌ **Instruções que não atendem o solicitado** → Item desconsiderado
- ❌ **Código com problemas de execução** → Item sem pontuação
- ❌ **Vídeo sem áudio ou baixa resolução** → Item desconsiderado

### Como Garantir Nota Máxima

✅ Todos os arquivos entregues  
✅ Código compilando sem erros  
✅ Aplicação rodando corretamente  
✅ Todos os endpoints testados  
✅ Documentação completa em PDF  
✅ Vídeo com boa qualidade e áudio claro  
✅ 10 documentos com +10 atributos cada  
✅ CRUD completo funcionando  

---

## 🎯 Próximos Passos

1. [ ] Converter documentação Markdown para PDF
2. [ ] Gravar vídeo seguindo roteiro
3. [ ] Fazer upload do vídeo (YouTube/Drive/Vimeo)
4. [ ] Criar arquivo ZIP com todos os arquivos
5. [ ] Enviar no portal da FIAP

---

**Status Geral:** 🟢 **87% Completo** (falta apenas o vídeo)

**Última Atualização:** Janeiro 2025
