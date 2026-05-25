package com.alura.dashboard.controller.mongodb;

import com.alura.dashboard.model.mongodb.CursoMongo;
import com.alura.dashboard.service.mongodb.CursoMongoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador REST para Cursos em MongoDB
 * Implementa operações CRUD e consultas especializadas
 */
@RestController
@RequestMapping("/api/mongodb/cursos")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CursoMongoController {

    private final CursoMongoService service;

    public CursoMongoController(CursoMongoService service) {
        this.service = service;
    }

    // ==================== CREATE ====================

    /**
     * Cria um novo curso
     * POST /api/mongodb/cursos
     */
    @PostMapping
    public ResponseEntity<CursoMongo> criar(@RequestBody CursoMongo curso) {
        CursoMongo cursoSalvo = service.criar(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoSalvo);
    }

    /**
     * Cria múltiplos cursos em lote
     * POST /api/mongodb/cursos/lote
     */
    @PostMapping("/lote")
    public ResponseEntity<List<CursoMongo>> criarLote(@RequestBody List<CursoMongo> cursos) {
        List<CursoMongo> cursosSalvos = service.criarLote(cursos);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursosSalvos);
    }

    // ==================== READ ====================

    /**
     * Obtém todos os cursos
     * GET /api/mongodb/cursos
     */
    @GetMapping
    public ResponseEntity<List<CursoMongo>> obterTodos() {
        List<CursoMongo> cursos = service.obterTodos();
        return ResponseEntity.ok(cursos);
    }

    /**
     * Obtém um curso por ID
     * GET /api/mongodb/cursos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<CursoMongo> obterPorId(@PathVariable String id) {
        Optional<CursoMongo> curso = service.obterPorId(id);
        return curso.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Busca cursos por nome
     * GET /api/mongodb/cursos/busca/nome?nome=Java
     */
    @GetMapping("/busca/nome")
    public ResponseEntity<List<CursoMongo>> buscarPorNome(@RequestParam String nome) {
        List<CursoMongo> cursos = service.buscarPorNome(nome);
        return ResponseEntity.ok(cursos);
    }

    /**
     * Busca cursos por categoria
     * GET /api/mongodb/cursos/busca/categoria?id={categoriaId}
     */
    @GetMapping("/busca/categoria")
    public ResponseEntity<List<CursoMongo>> buscarPorCategoria(@RequestParam String id) {
        List<CursoMongo> cursos = service.buscarPorCategoria(id);
        return ResponseEntity.ok(cursos);
    }

    /**
     * Busca cursos por instrutor
     * GET /api/mongodb/cursos/busca/instrutor?id={instrutorId}
     */
    @GetMapping("/busca/instrutor")
    public ResponseEntity<List<CursoMongo>> buscarPorInstrutor(@RequestParam String id) {
        List<CursoMongo> cursos = service.buscarPorInstrutor(id);
        return ResponseEntity.ok(cursos);
    }

    /**
     * Busca cursos por tag
     * GET /api/mongodb/cursos/busca/tag?tag=java
     */
    @GetMapping("/busca/tag")
    public ResponseEntity<List<CursoMongo>> buscarPorTag(@RequestParam String tag) {
        List<CursoMongo> cursos = service.buscarPorTag(tag);
        return ResponseEntity.ok(cursos);
    }

    /**
     * Obtém top 5 cursos por popularidade
     * GET /api/mongodb/cursos/populares/top5
     */
    @GetMapping("/populares/top5")
    public ResponseEntity<List<CursoMongo>> obterTop5Popular() {
        List<CursoMongo> cursos = service.obterTop5Popular();
        return ResponseEntity.ok(cursos);
    }

    /**
     * Obtém top 5 cursos por nota
     * GET /api/mongodb/cursos/notas/top5
     */
    @GetMapping("/notas/top5")
    public ResponseEntity<List<CursoMongo>> obterTop5Nota() {
        List<CursoMongo> cursos = service.obterTop5Nota();
        return ResponseEntity.ok(cursos);
    }

    /**
     * Obtém cursos com popularidade mínima
     * GET /api/mongodb/cursos/populares?min=1000
     */
    @GetMapping("/populares")
    public ResponseEntity<List<CursoMongo>> obterCursosPorPopularidade(@RequestParam Integer min) {
        List<CursoMongo> cursos = service.obterCursosPorPopularidade(min);
        return ResponseEntity.ok(cursos);
    }

    /**
     * Obtém cursos com nota mínima
     * GET /api/mongodb/cursos/notas?min=4.0
     */
    @GetMapping("/notas")
    public ResponseEntity<List<CursoMongo>> obterCursosPorNota(@RequestParam Double min) {
        List<CursoMongo> cursos = service.obterCursosPorNota(min);
        return ResponseEntity.ok(cursos);
    }

    /**
     * Obtém cursos por intervalo de nota
     * GET /api/mongodb/cursos/notas/intervalo?min=4.0&max=5.0
     */
    @GetMapping("/notas/intervalo")
    public ResponseEntity<List<CursoMongo>> obterCursosPorIntervalo(
            @RequestParam Double min,
            @RequestParam Double max) {
        List<CursoMongo> cursos = service.obterCursosPorIntervaloNota(min, max);
        return ResponseEntity.ok(cursos);
    }

    /**
     * Obtém apenas cursos ativos
     * GET /api/mongodb/cursos/ativos
     */
    @GetMapping("/ativos")
    public ResponseEntity<List<CursoMongo>> obterCursosAtivos() {
        List<CursoMongo> cursos = service.obterCursosAtivos();
        return ResponseEntity.ok(cursos);
    }

    /**
     * Obtém estatísticas gerais
     * GET /api/mongodb/cursos/stats/gerais
     */
    @GetMapping("/stats/gerais")
    public ResponseEntity<Map<String, Object>> obterEstatisticas() {
        Map<String, Object> stats = service.obterEstatisticas();
        return ResponseEntity.ok(stats);
    }

    // ==================== UPDATE ====================

    /**
     * Atualiza um curso existente
     * PUT /api/mongodb/cursos/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<CursoMongo> atualizar(
            @PathVariable String id,
            @RequestBody CursoMongo cursoAtualizado) {
        try {
            CursoMongo cursoAtual = service.atualizar(id, cursoAtualizado);
            return ResponseEntity.ok(cursoAtual);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Adiciona avaliação a um curso
     * POST /api/mongodb/cursos/{id}/avaliacoes
     */
    @PostMapping("/{id}/avaliacoes")
    public ResponseEntity<CursoMongo> adicionarAvaliacao(
            @PathVariable String id,
            @RequestBody CursoMongo.Avaliacao avaliacao) {
        try {
            CursoMongo cursoAtualizado = service.adicionarAvaliacao(id, avaliacao);
            return ResponseEntity.ok(cursoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Incrementa popularidade de um curso
     * PATCH /api/mongodb/cursos/{id}/popularidade
     */
    @PatchMapping("/{id}/popularidade")
    public ResponseEntity<CursoMongo> incrementarPopularidade(
            @PathVariable String id,
            @RequestParam Integer quantidade) {
        try {
            CursoMongo cursoAtualizado = service.incrementarPopularidade(id, quantidade);
            return ResponseEntity.ok(cursoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ==================== DELETE ====================

    /**
     * Deleta um curso completamente
     * DELETE /api/mongodb/cursos/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        try {
            service.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Desativa um curso (delete lógico)
     * DELETE /api/mongodb/cursos/{id}/desativar
     */
    @DeleteMapping("/{id}/desativar")
    public ResponseEntity<CursoMongo> desativar(@PathVariable String id) {
        try {
            CursoMongo cursoDesativado = service.desativar(id);
            return ResponseEntity.ok(cursoDesativado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Reativa um curso desativado
     * POST /api/mongodb/cursos/{id}/reativar
     */
    @PostMapping("/{id}/reativar")
    public ResponseEntity<CursoMongo> reativar(@PathVariable String id) {
        try {
            CursoMongo cursoReativado = service.reativar(id);
            return ResponseEntity.ok(cursoReativado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deleta múltiplos cursos
     * DELETE /api/mongodb/cursos/lote
     */
    @DeleteMapping("/lote")
    public ResponseEntity<Void> deletarMultiplos(@RequestBody List<String> ids) {
        service.deletarMultiplos(ids);
        return ResponseEntity.noContent().build();
    }

    // ==================== HEALTH CHECK ====================

    /**
     * Verifica saúde da API
     * GET /api/mongodb/cursos/health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Cursos MongoDB API");
        response.put("timestamp", java.time.LocalDateTime.now().toString());
        return ResponseEntity.ok(response);
    }
}
