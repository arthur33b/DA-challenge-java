package com.alura.dashboard.client;

import com.alura.dashboard.dto.CursoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "cursoClient", url = "${app.local-url}")
public interface CursoFeignClient {

    @GetMapping("/api/cursos")
    List<CursoDTO> listarTodosCursos();
}
