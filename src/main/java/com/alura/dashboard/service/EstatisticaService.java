package com.alura.dashboard.service;

import com.alura.dashboard.client.CursoFeignClient;
import com.alura.dashboard.dto.CursoDTO;
import com.alura.dashboard.dto.EstatisticaPorCategoriaDTO;
import com.alura.dashboard.dto.MediaAvaliacaoPorCategoriaDTO;
import com.alura.dashboard.dto.TopCursoDTO;
import com.alura.dashboard.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EstatisticaService {

    private final CursoRepository cursoRepository;
    private final CursoFeignClient cursoFeignClient;

    public EstatisticaService(CursoRepository cursoRepository, CursoFeignClient cursoFeignClient) {
        this.cursoRepository = cursoRepository;
        this.cursoFeignClient = cursoFeignClient;
    }

    public List<EstatisticaPorCategoriaDTO> getQuantidadeCursosPorCategoria() {
        Map<String, Long> contagem = cursoRepository.findAll().stream()
                .collect(Collectors.groupingBy(curso -> curso.getCategoria().getNome(), Collectors.counting()));
        
        return contagem.entrySet().stream()
                .map(entry -> new EstatisticaPorCategoriaDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public List<TopCursoDTO> getTop5CursosPopulares() {
        return cursoRepository.findAll().stream()
                .sorted((c1, c2) -> c2.getPopularidade().compareTo(c1.getPopularidade()))
                .limit(5)
                .map(curso -> new TopCursoDTO(curso.getId(), curso.getNome(), curso.getPopularidade(), curso.getNotaMedia()))
                .collect(Collectors.toList());
    }

    public List<MediaAvaliacaoPorCategoriaDTO> getMediaAvaliacoesPorArea() {
        return cursoFeignClient.listarTodosCursos().stream()
                .collect(Collectors.groupingBy(CursoDTO::getCategoria, Collectors.averagingDouble(CursoDTO::getNotaMedia)))
                .entrySet().stream()
                .map(entry -> new MediaAvaliacaoPorCategoriaDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}