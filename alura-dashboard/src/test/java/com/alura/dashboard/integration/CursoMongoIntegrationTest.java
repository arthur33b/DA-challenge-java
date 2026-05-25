package com.alura.dashboard.integration;

import com.alura.dashboard.mongodb.model.CursoMongo;
import com.alura.dashboard.mongodb.repository.CursoMongoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes de integração para endpoints MongoDB da API Alura Dashboard
 * 
 * Estes testes verificam:
 * - CRUD básico (Create, Read, Update, Delete)
 * - Operações de busca e filtros
 * - Endpoints especializados (Top 5, estatísticas, etc)
 * - Tratamento de erros
 * - Validações
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Testes de Integração - MongoDB API")
class CursoMongoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CursoMongoRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    private CursoMongo novoCurso;

    @BeforeEach
    void setup() {
        // Limpar dados antes de cada teste
        repository.deleteAll();
        
        // Criar curso de teste
        novoCurso = criarCursoTeste();
    }

    // ==================== TESTES DE CREATE ====================

    @Test
    @DisplayName("POST /api/mongodb/cursos - Criar novo curso com sucesso")
    void testCriarCurso() throws Exception {
        String jsonCurso = objectMapper.writeValueAsString(novoCurso);

        mockMvc.perform(post("/api/mongodb/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCurso))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.nome").value(novoCurso.getNome()))
                .andExpect(jsonPath("$.descricao").value(novoCurso.getDescricao()))
                .andExpect(jsonPath("$.ativo").value(true));
    }

    @Test
    @DisplayName("POST /api/mongodb/cursos - Falha ao criar curso sem nome")
    void testCriarCursoSemNome() throws Exception {
        novoCurso.setNome(null);
        String jsonCurso = objectMapper.writeValueAsString(novoCurso);

        mockMvc.perform(post("/api/mongodb/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCurso))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/mongodb/cursos/lote - Criar múltiplos cursos")
    void testCriarCursoEmLote() throws Exception {
        CursoMongo curso1 = criarCursoTeste();
        CursoMongo curso2 = criarCursoTeste();
        curso2.setNome("Curso 2");

        String jsonLote = objectMapper.writeValueAsString(java.util.Arrays.asList(curso1, curso2));

        mockMvc.perform(post("/api/mongodb/cursos/lote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonLote))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    // ==================== TESTES DE READ ====================

    @Test
    @DisplayName("GET /api/mongodb/cursos - Listar todos os cursos")
    void testListarTodosCursos() throws Exception {
        repository.save(novoCurso);

        mockMvc.perform(get("/api/mongodb/cursos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].nome").exists());
    }

    @Test
    @DisplayName("GET /api/mongodb/cursos/{id} - Buscar curso por ID")
    void testBuscarCursoPorId() throws Exception {
        CursoMongo cursoSalvo = repository.save(novoCurso);

        mockMvc.perform(get("/api/mongodb/cursos/{id}", cursoSalvo.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cursoSalvo.getId()))
                .andExpect(jsonPath("$.nome").value(novoCurso.getNome()));
    }

    @Test
    @DisplayName("GET /api/mongodb/cursos/{id} - Retorna 404 para ID inválido")
    void testBuscarCursoPorIdInvalido() throws Exception {
        mockMvc.perform(get("/api/mongodb/cursos/invalidId")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/mongodb/cursos/buscar/nome - Buscar por nome")
    void testBuscarPorNome() throws Exception {
        repository.save(novoCurso);

        mockMvc.perform(get("/api/mongodb/cursos/buscar/nome")
                .param("nome", "Java")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    @DisplayName("GET /api/mongodb/cursos/top5/popularidade - Top 5 mais populares")
    void testTop5Popularidade() throws Exception {
        repository.save(novoCurso);

        mockMvc.perform(get("/api/mongodb/cursos/top5/popularidade")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(lessThanOrEqualTo(5))));
    }

    @Test
    @DisplayName("GET /api/mongodb/cursos/top5/nota - Top 5 melhor avaliados")
    void testTop5Nota() throws Exception {
        repository.save(novoCurso);

        mockMvc.perform(get("/api/mongodb/cursos/top5/nota")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(lessThanOrEqualTo(5))));
    }

    @Test
    @DisplayName("GET /api/mongodb/cursos/estatisticas - Obter estatísticas")
    void testObtenerEstatisticas() throws Exception {
        repository.save(novoCurso);

        mockMvc.perform(get("/api/mongodb/cursos/estatisticas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCursos").isNumber())
                .andExpect(jsonPath("$.mediaPopularidade").isNumber())
                .andExpect(jsonPath("$.mediaNotaMedia").isNumber());
    }

    // ==================== TESTES DE UPDATE ====================

    @Test
    @DisplayName("PUT /api/mongodb/cursos/{id} - Atualizar curso")
    void testAtualizarCurso() throws Exception {
        CursoMongo cursoSalvo = repository.save(novoCurso);
        cursoSalvo.setNome("Novo Nome");
        cursoSalvo.setPopularidade(5000);

        String jsonCurso = objectMapper.writeValueAsString(cursoSalvo);

        mockMvc.perform(put("/api/mongodb/cursos/{id}", cursoSalvo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCurso))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Novo Nome"))
                .andExpect(jsonPath("$.popularidade").value(5000));
    }

    @Test
    @DisplayName("PATCH /api/mongodb/cursos/{id}/avaliacoes - Adicionar avaliação")
    void testAdicionarAvaliacao() throws Exception {
        CursoMongo cursoSalvo = repository.save(novoCurso);

        String avaliacaoJson = objectMapper.writeValueAsString(
            java.util.Map.of(
                "usuario", "usuario_teste",
                "nota", 4.5,
                "comentario", "Excelente curso!"
            )
        );

        mockMvc.perform(patch("/api/mongodb/cursos/{id}/avaliacoes", cursoSalvo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(avaliacaoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.avaliacoes", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @DisplayName("PATCH /api/mongodb/cursos/{id}/popularidade - Incrementar popularidade")
    void testIncrementarPopularidade() throws Exception {
        CursoMongo cursoSalvo = repository.save(novoCurso);
        int popularidadeInicial = cursoSalvo.getPopularidade();

        mockMvc.perform(patch("/api/mongodb/cursos/{id}/popularidade", cursoSalvo.getId())
                .param("incremento", "100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.popularidade").value(popularidadeInicial + 100));
    }

    @Test
    @DisplayName("PATCH /api/mongodb/cursos/{id}/desativar - Desativar curso")
    void testDesativarCurso() throws Exception {
        CursoMongo cursoSalvo = repository.save(novoCurso);

        mockMvc.perform(patch("/api/mongodb/cursos/{id}/desativar", cursoSalvo.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ativo").value(false));
    }

    @Test
    @DisplayName("PATCH /api/mongodb/cursos/{id}/reativar - Reativar curso")
    void testReativarCurso() throws Exception {
        novoCurso.setAtivo(false);
        CursoMongo cursoSalvo = repository.save(novoCurso);

        mockMvc.perform(patch("/api/mongodb/cursos/{id}/reativar", cursoSalvo.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ativo").value(true));
    }

    // ==================== TESTES DE DELETE ====================

    @Test
    @DisplayName("DELETE /api/mongodb/cursos/{id} - Deletar curso")
    void testDeletarCurso() throws Exception {
        CursoMongo cursoSalvo = repository.save(novoCurso);

        mockMvc.perform(delete("/api/mongodb/cursos/{id}", cursoSalvo.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verificar que o curso foi deletado
        mockMvc.perform(get("/api/mongodb/cursos/{id}", cursoSalvo.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/mongodb/cursos/{id} - Retorna 404 para ID inválido")
    void testDeletarCursoInvalido() throws Exception {
        mockMvc.perform(delete("/api/mongodb/cursos/invalidId")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // ==================== TESTES DE VALIDAÇÃO ====================

    @Test
    @DisplayName("Validar campos obrigatórios")
    void testValidarCamposObrigatorios() throws Exception {
        CursoMongo cursoInvalido = new CursoMongo();
        // Nome e descrição são obrigatórios
        String jsonCurso = objectMapper.writeValueAsString(cursoInvalido);

        mockMvc.perform(post("/api/mongodb/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCurso))
                .andExpect(status().isBadRequest());
    }

    // ==================== TESTES DE PERFORMANCE ====================

    @Test
    @DisplayName("GET /api/mongodb/cursos - Performance com múltiplos documentos")
    void testPerformanceListagemGrande() throws Exception {
        // Inserir 100 cursos
        for (int i = 0; i < 100; i++) {
            CursoMongo curso = criarCursoTeste();
            curso.setNome("Curso " + i);
            repository.save(curso);
        }

        long inicio = System.currentTimeMillis();
        
        MvcResult result = mockMvc.perform(get("/api/mongodb/cursos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        long duracao = System.currentTimeMillis() - inicio;
        
        // Verificar que levou menos de 1 segundo (ajustar conforme necessário)
        assert duracao < 1000 : "Query levou muito tempo: " + duracao + "ms";
    }

    // ==================== MÉTODOS AUXILIARES ====================

    private CursoMongo criarCursoTeste() {
        CursoMongo curso = new CursoMongo();
        curso.setNome("Java Avançado - Spring Boot Masterclass");
        curso.setDescricao("Aprenda os conceitos avançados de Java com foco em Spring Boot");
        curso.setPopularidade(2500);
        curso.setNotaMedia(4.9);
        curso.setAtivo(true);
        curso.setMatriculados(2500);
        curso.setCertificadoDisponivel(true);
        
        // Categoria
        CursoMongo.CategoriaMongo categoria = new CursoMongo.CategoriaMongo();
        categoria.setId("1");
        categoria.setNome("Backend");
        categoria.setDescricao("Desenvolvimento backend");
        curso.setCategoria(categoria);
        
        // Instrutor
        CursoMongo.InstrutorMongo instrutor = new CursoMongo.InstrutorMongo();
        instrutor.setId("1");
        instrutor.setNome("Arthur Borges");
        instrutor.setEmail("arthur@alura.com");
        instrutor.setEspecializacao("Java Enterprise");
        instrutor.setExperienciaAnos(10);
        curso.setInstrutor(instrutor);
        
        // Tags
        curso.setTags(java.util.Arrays.asList("java", "spring-boot", "backend"));
        
        return curso;
    }
}
