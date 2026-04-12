package com.alura.dashboard.repository;

import com.alura.dashboard.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    List<Curso> findByCategoriaId(Long categoriaId);
    List<Curso> findTop5ByOrderByPopularidadeDesc();
}