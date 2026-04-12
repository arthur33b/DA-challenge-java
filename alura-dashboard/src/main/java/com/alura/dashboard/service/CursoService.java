package com.alura.dashboard.service;

import com.alura.dashboard.dto.CursoDTO;
import com.alura.dashboard.model.Curso;
import com.alura.dashboard.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    @Autowired
    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
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

    public List<CursoDTO> listarCursosPorCategoria(Long categoriaId) {
        return cursoRepository.findByCategoriaId(categoriaId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CursoDTO> listarCursosPopulares() {
        return cursoRepository.findTop5ByOrderByPopularidadeDesc().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}