package com.alura.dashboard.controller;

import com.alura.dashboard.dto.EstatisticaPorCategoriaDTO;
import com.alura.dashboard.dto.TopCursoDTO;
import com.alura.dashboard.service.EstatisticaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/estatisticas")
@Tag(name = "Estatísticas", description = "Endpoints para consulta de estatísticas dos cursos")
public class EstatisticaController {

    @Autowired
    private EstatisticaService estatisticaService;

    @GetMapping("/por-categoria")
    @Operation(summary = "Retorna a quantidade de cursos por categoria")
    public ResponseEntity<List<EstatisticaPorCategoriaDTO>> getQuantidadeCursosPorCategoria() {
        return ResponseEntity.ok(estatisticaService.getQuantidadeCursosPorCategoria());
    }

    @GetMapping("/top5")
    @Operation(summary = "Retorna os 5 cursos mais populares")
    public ResponseEntity<List<TopCursoDTO>> getTop5CursosPopulares() {
        return ResponseEntity.ok(estatisticaService.getTop5CursosPopulares());
    }

    @GetMapping("/avaliacoes")
    @Operation(summary = "Retorna a média de avaliações por área")
    public ResponseEntity<List<Double>> getMediaAvaliacoesPorArea() {
        return ResponseEntity.ok(estatisticaService.getMediaAvaliacoesPorArea());
    }
}