package com.alura.dashboard.controller;

import com.alura.dashboard.dto.CursoDTO;
import com.alura.dashboard.service.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@Tag(name = "Cursos", description = "Endpoints para gerenciamento de cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    @Operation(summary = "Lista todos os cursos disponíveis")
    public ResponseEntity<List<CursoDTO>> listarCursos() {
        return ResponseEntity.ok(cursoService.listarTodosCursos());
    }

    @GetMapping("/categoria/{id}")
    @Operation(summary = "Lista cursos por categoria")
    public ResponseEntity<List<CursoDTO>> listarCursosPorCategoria(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.listarCursosPorCategoria(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna detalhes de um curso específico")
    public ResponseEntity<CursoDTO> buscarCursoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.buscarCursoPorId(id));
    }

    @GetMapping("/classificacao/{id}")
    @Operation(summary = "Retorna a classificação de um curso")
    public ResponseEntity<String> obterClassificacao(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.classificarCurso(id));
    }

    @GetMapping("/populares")
    public ResponseEntity<List<CursoDTO>> listarCursosPopulares() {
        List<CursoDTO> cursosPopulares = cursoService.listarCursosPopulares();
        return ResponseEntity.ok(cursosPopulares);
    }
}