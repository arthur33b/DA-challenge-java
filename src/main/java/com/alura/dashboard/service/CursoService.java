package com.alura.dashboard.service;

import com.alura.dashboard.client.CursoFeignClient;
import com.alura.dashboard.dto.CursoConsultaEventoDTO;
import com.alura.dashboard.dto.CursoDTO;
import com.alura.dashboard.messaging.CursoEventProducer;
import com.alura.dashboard.model.Curso;
import com.alura.dashboard.repository.CursoRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final CursoFeignClient cursoFeignClient;
    private final CursoEventProducer cursoEventProducer;

    public CursoService(CursoRepository cursoRepository,
                        CursoFeignClient cursoFeignClient,
                        CursoEventProducer cursoEventProducer) {
        this.cursoRepository = cursoRepository;
        this.cursoFeignClient = cursoFeignClient;
        this.cursoEventProducer = cursoEventProducer;
    }

    private CursoDTO convertToDTO(Curso curso) {
        return new CursoDTO(
            curso.getId(),
            curso.getNome(),
            curso.getDescricao(),
            curso.getCategoria().getNome(),
            curso.getInstrutor().getNome(),
            curso.getPopularidade(),
            curso.getNotaMedia()
        );
    }

    public List<CursoDTO> listarTodosCursos() {
        return cursoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CursoDTO buscarCursoPorId(Long id) {
        return cursoRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado: " + id));
    }

    public List<CursoDTO> listarCursosPorCategoria(Long categoriaId) {
        var cursos = cursoRepository.findByCategoriaId(categoriaId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        cursoEventProducer.publish(new CursoConsultaEventoDTO(
                categoriaId,
                "CURSOS_POR_CATEGORIA",
                getCurrentUsuario(),
                LocalDateTime.now()
        ));

        return cursos;
    }

    public List<CursoDTO> listarCursosPopulares() {
        var cursos = cursoFeignClient.listarTodosCursos();
        var populares = cursos.stream()
                .sorted((c1, c2) -> c2.getPopularidade().compareTo(c1.getPopularidade()))
                .limit(5)
                .collect(Collectors.toList());

        cursoEventProducer.publish(new CursoConsultaEventoDTO(
                null,
                "TOP_POPULARES",
                getCurrentUsuario(),
                LocalDateTime.now()
        ));

        return populares;
    }

    public String classificarCurso(Long id) {
        var curso = buscarCursoPorId(id);

        if (curso.getPopularidade() == null || curso.getNotaMedia() == null) {
            return "DADOS_INCOMPLETOS";
        }

        if (curso.getPopularidade() > 3000 && curso.getNotaMedia() >= 4.5) {
            return "TOP";
        }
        if (curso.getPopularidade() > 1500) {
            return "BOM";
        }
        return "NORMAL";
    }

    private String getCurrentUsuario() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : "anonymous";
    }
}
