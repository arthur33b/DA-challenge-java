package com.alura.dashboard.controller;

import com.alura.dashboard.dto.CursoDTO;
import com.alura.dashboard.service.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<CursoDTO> cursos = cursoService.listarTodosCursos();
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<List<CursoDTO>> listarCursosPorCategoria(@PathVariable Long id) {
        List<CursoDTO> cursos = cursoService.listarCursosPorCategoria(id);
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/populares")
    public ResponseEntity<List<CursoDTO>> listarCursosPopulares() {
        List<CursoDTO> cursosPopulares = cursoService.listarCursosPopulares();
        return ResponseEntity.ok(cursosPopulares);
    }
}