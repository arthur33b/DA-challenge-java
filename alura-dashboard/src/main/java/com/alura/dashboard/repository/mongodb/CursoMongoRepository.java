package com.alura.dashboard.repository.mongodb;

import com.alura.dashboard.model.mongodb.CursoMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório MongoDB para Curso
 * Fornece operações CRUD e consultas customizadas
 */
@Repository
public interface CursoMongoRepository extends MongoRepository<CursoMongo, String> {

    // Buscas por nome
    List<CursoMongo> findByNomeContainingIgnoreCase(String nome);

    // Buscas por categoria
    List<CursoMongo> findByCategoriaId(String categoriaId);
    List<CursoMongo> findByCategoriaNome(String categoriaNome);

    // Buscas por instrutor
    List<CursoMongo> findByInstrutorId(String instrutorId);
    List<CursoMongo> findByInstrutorNome(String instrutorNome);

    // Buscas por popularidade
    List<CursoMongo> findByPopularidadeGreaterThanOrderByPopularidadeDesc(Integer min);

    // Buscas por nota
    List<CursoMongo> findByNotaMediaGreaterThanEqualOrderByNotaMediaDesc(Double minNota);

    // Buscas por tags
    List<CursoMongo> findByTagsContaining(String tag);

    // Buscas por status
    List<CursoMongo> findByAtivo(Boolean ativo);

    // Top cursos por popularidade
    List<CursoMongo> findTop5ByOrderByPopularidadeDesc();

    // Top cursos por nota
    List<CursoMongo> findTop5ByOrderByNotaMediaDesc();

    // Query customizada: cursos por intervalo de nota
    @Query("{ 'notaMedia': { $gte: ?0, $lte: ?1 } }")
    List<CursoMongo> findCursosByNotaMediaRange(Double minNota, Double maxNota);

    // Query customizada: cursos ativos com alta popularidade
    @Query("{ 'ativo': true, 'popularidade': { $gte: ?0 } }")
    List<CursoMongo> findCursosAtivosPorPopularidade(Integer minPopularidade);

    // Contar cursos por categoria
    Long countByCategoriaId(String categoriaId);

    // Verificar existência por nome
    boolean existsByNome(String nome);
}
