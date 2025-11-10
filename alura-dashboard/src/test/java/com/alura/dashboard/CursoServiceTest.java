package com.alura.dashboard;

import com.alura.dashboard.dto.CursoDTO;
import com.alura.dashboard.model.Categoria;
import com.alura.dashboard.model.Curso;
import com.alura.dashboard.model.Instrutor;
import com.alura.dashboard.repository.CursoRepository;
import com.alura.dashboard.service.CursoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CursoServiceTest {

    @InjectMocks
    private CursoService cursoService;

    @Mock
    private CursoRepository cursoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarTodosCursos() {
        Categoria categoria = new Categoria();
        categoria.setNome("Programação");
        
        Instrutor instrutor = new Instrutor();
        instrutor.setNome("João");

        Curso curso1 = new Curso(1L, "Curso Java", "Aprenda Java", categoria, instrutor, 100, 4.5);
        Curso curso2 = new Curso(2L, "Curso Spring", "Aprenda Spring", categoria, instrutor, 150, 4.7);
        List<Curso> cursos = Arrays.asList(curso1, curso2);

        when(cursoRepository.findAll()).thenReturn(cursos);

        List<CursoDTO> result = cursoService.listarTodosCursos();

        assertEquals(2, result.size());
        assertEquals("Curso Java", result.get(0).getNome());
        assertEquals("Curso Spring", result.get(1).getNome());
    }

    @Test
    public void testListarCursosPopulares() {
        Categoria categoria = new Categoria();
        categoria.setNome("Programação");
        
        Instrutor instrutor = new Instrutor();
        instrutor.setNome("João");

        Curso curso1 = new Curso(1L, "Curso Java", "Aprenda Java", categoria, instrutor, 200, 4.5);
        Curso curso2 = new Curso(2L, "Curso Spring", "Aprenda Spring", categoria, instrutor, 300, 4.7);
        List<Curso> cursos = Arrays.asList(curso1, curso2);

        when(cursoRepository.findTop5ByOrderByPopularidadeDesc()).thenReturn(cursos);

        List<CursoDTO> result = cursoService.listarCursosPopulares();

        assertEquals(2, result.size());
        assertEquals("Curso Spring", result.get(1).getNome());
        assertEquals("Curso Java", result.get(0).getNome());
    }
}