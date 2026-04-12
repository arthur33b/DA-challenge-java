package com.alura.dashboard;

import com.alura.dashboard.controller.CursoController;
import com.alura.dashboard.dto.CursoDTO;
import com.alura.dashboard.service.CursoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CursoControllerTest {

        private MockMvc mockMvc;

        private CursoService cursoService;

        @org.junit.jupiter.api.BeforeEach
        public void setUp() {
                // Use a simple fake service implementation to avoid Mockito/ByteBuddy inline issues on newer JVMs
                cursoService = new FakeCursoService();
                mockMvc = org.springframework.test.web.servlet.setup.MockMvcBuilders
                        .standaloneSetup(new com.alura.dashboard.controller.CursoController(cursoService))
                        .build();
        }
        @Test
        public void testGetAllCursos() throws Exception {
        List<CursoDTO> cursos = Arrays.asList(
                new CursoDTO(1L, "Curso 1", "Descrição 1", "Programação", "João", 100, 4.5),
                new CursoDTO(2L, "Curso 2", "Descrição 2", "Programação", "João", 200, 4.7)
        );

                ((FakeCursoService)cursoService).setAll(cursos);

        mockMvc.perform(get("/api/cursos").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Curso 1"))
                .andExpect(jsonPath("$[1].nome").value("Curso 2"));
    }

        @Test
        public void testGetCursosByCategoria() throws Exception {
        List<CursoDTO> cursos = Arrays.asList(
                new CursoDTO(1L, "Curso 1", "Descrição 1", "Programação", "João", 100, 4.5)
        );

                ((FakeCursoService)cursoService).setByCategoria(cursos);

        mockMvc.perform(get("/api/cursos/categoria/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Curso 1"));
    }

        @Test
        public void testGetCursosPopulares() throws Exception {
        List<CursoDTO> cursos = Arrays.asList(
                new CursoDTO(1L, "Curso 1", "Descrição 1", "Programação", "João", 200, 4.5),
                new CursoDTO(2L, "Curso 2", "Descrição 2", "Programação", "João", 150, 4.7)
        );

                        ((FakeCursoService)cursoService).setPopular(cursos);

        mockMvc.perform(get("/api/cursos/populares").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Curso 1"))
                .andExpect(jsonPath("$[1].nome").value("Curso 2"));
    }

                // Simple fake service used by tests to avoid Mockito inline mocking on unsupported JVMs
                static class FakeCursoService extends com.alura.dashboard.service.CursoService {
                        private java.util.List<com.alura.dashboard.dto.CursoDTO> all = java.util.Collections.emptyList();
                        private java.util.List<com.alura.dashboard.dto.CursoDTO> byCategoria = java.util.Collections.emptyList();
                        private java.util.List<com.alura.dashboard.dto.CursoDTO> popular = java.util.Collections.emptyList();

                        public FakeCursoService() {
                                super(null);
                        }

                        void setAll(java.util.List<com.alura.dashboard.dto.CursoDTO> list) { this.all = list; }
                        void setByCategoria(java.util.List<com.alura.dashboard.dto.CursoDTO> list) { this.byCategoria = list; }
                        void setPopular(java.util.List<com.alura.dashboard.dto.CursoDTO> list) { this.popular = list; }

                        @Override
                        public java.util.List<com.alura.dashboard.dto.CursoDTO> listarTodosCursos() {
                                return all;
                        }

                        @Override
                        public java.util.List<com.alura.dashboard.dto.CursoDTO> listarCursosPorCategoria(Long categoriaId) {
                                return byCategoria;
                        }

                        @Override
                        public java.util.List<com.alura.dashboard.dto.CursoDTO> listarCursosPopulares() {
                                return popular;
                        }
                }
}