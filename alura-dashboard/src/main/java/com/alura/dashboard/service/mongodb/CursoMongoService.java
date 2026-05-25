package com.alura.dashboard.service.mongodb;

import com.alura.dashboard.model.mongodb.CursoMongo;
import com.alura.dashboard.repository.mongodb.CursoMongoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Serviço MongoDB para operações CRUD em Cursos
 * Implementa a lógica de negócio com MongoDB
 */
@Service
public class CursoMongoService {

    private final CursoMongoRepository repository;

    public CursoMongoService(CursoMongoRepository repository) {
        this.repository = repository;
    }

    // ==================== CREATE ====================

    /**
     * Cria um novo curso no MongoDB
     */
    public CursoMongo criar(CursoMongo curso) {
        curso.setDataModificacao(LocalDateTime.now());
        curso.setAtivo(true);
        return repository.save(curso);
    }

    /**
     * Cria múltiplos cursos em lote
     */
    public List<CursoMongo> criarLote(List<CursoMongo> cursos) {
        return repository.saveAll(cursos);
    }

    // ==================== READ ====================

    /**
     * Obtém todos os cursos
     */
    public List<CursoMongo> obterTodos() {
        return repository.findAll();
    }

    /**
     * Obtém um curso por ID
     */
    public Optional<CursoMongo> obterPorId(String id) {
        return repository.findById(id);
    }

    /**
     * Obtém curso por ID ou lança exceção
     */
    public CursoMongo obterPorIdOuLancarExcecao(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado: " + id));
    }

    /**
     * Busca cursos por nome
     */
    public List<CursoMongo> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    /**
     * Busca cursos por categoria
     */
    public List<CursoMongo> buscarPorCategoria(String categoriaId) {
        return repository.findByCategoriaId(categoriaId);
    }

    /**
     * Busca cursos por instrutor
     */
    public List<CursoMongo> buscarPorInstrutor(String instrutorId) {
        return repository.findByInstrutorId(instrutorId);
    }

    /**
     * Busca cursos por tag
     */
    public List<CursoMongo> buscarPorTag(String tag) {
        return repository.findByTagsContaining(tag);
    }

    /**
     * Obtém top 5 cursos por popularidade
     */
    public List<CursoMongo> obterTop5Popular() {
        return repository.findTop5ByOrderByPopularidadeDesc();
    }

    /**
     * Obtém top 5 cursos por nota
     */
    public List<CursoMongo> obterTop5Nota() {
        return repository.findTop5ByOrderByNotaMediaDesc();
    }

    /**
     * Obtém cursos com popularidade mínima
     */
    public List<CursoMongo> obterCursosPorPopularidade(Integer minPopularidade) {
        return repository.findByPopularidadeGreaterThanOrderByPopularidadeDesc(minPopularidade);
    }

    /**
     * Obtém cursos com nota mínima
     */
    public List<CursoMongo> obterCursosPorNota(Double minNota) {
        return repository.findByNotaMediaGreaterThanEqualOrderByNotaMediaDesc(minNota);
    }

    /**
     * Obtém cursos por intervalo de nota
     */
    public List<CursoMongo> obterCursosPorIntervaloNota(Double minNota, Double maxNota) {
        return repository.findCursosByNotaMediaRange(minNota, maxNota);
    }

    /**
     * Obtém cursos ativos com popularidade mínima
     */
    public List<CursoMongo> obterCursosAtivosPorPopularidade(Integer minPopularidade) {
        return repository.findCursosAtivosPorPopularidade(minPopularidade);
    }

    /**
     * Obtém apenas cursos ativos
     */
    public List<CursoMongo> obterCursosAtivos() {
        return repository.findByAtivo(true);
    }

    /**
     * Conta cursos por categoria
     */
    public Long contarPorCategoria(String categoriaId) {
        return repository.countByCategoriaId(categoriaId);
    }

    /**
     * Verifica se curso existe por nome
     */
    public boolean existePorNome(String nome) {
        return repository.existsByNome(nome);
    }

    // ==================== UPDATE ====================

    /**
     * Atualiza um curso existente
     */
    public CursoMongo atualizar(String id, CursoMongo cursoAtualizado) {
        CursoMongo cursoExistente = obterPorIdOuLancarExcecao(id);

        if (cursoAtualizado.getNome() != null) {
            cursoExistente.setNome(cursoAtualizado.getNome());
        }
        if (cursoAtualizado.getDescricao() != null) {
            cursoExistente.setDescricao(cursoAtualizado.getDescricao());
        }
        if (cursoAtualizado.getPopularidade() != null) {
            cursoExistente.setPopularidade(cursoAtualizado.getPopularidade());
        }
        if (cursoAtualizado.getNotaMedia() != null) {
            cursoExistente.setNotaMedia(cursoAtualizado.getNotaMedia());
        }
        if (cursoAtualizado.getCategoria() != null) {
            cursoExistente.setCategoria(cursoAtualizado.getCategoria());
        }
        if (cursoAtualizado.getInstrutor() != null) {
            cursoExistente.setInstrutor(cursoAtualizado.getInstrutor());
        }
        if (cursoAtualizado.getTags() != null) {
            cursoExistente.setTags(cursoAtualizado.getTags());
        }
        if (cursoAtualizado.getAtivo() != null) {
            cursoExistente.setAtivo(cursoAtualizado.getAtivo());
        }

        cursoExistente.setDataModificacao(LocalDateTime.now());
        return repository.save(cursoExistente);
    }

    /**
     * Adiciona avaliação a um curso
     */
    public CursoMongo adicionarAvaliacao(String cursoId, CursoMongo.Avaliacao avaliacao) {
        CursoMongo curso = obterPorIdOuLancarExcecao(cursoId);

        if (curso.getAvaliacoes() == null) {
            curso.setAvaliacoes(new java.util.ArrayList<>());
        }

        avaliacao.setData(LocalDateTime.now());
        curso.getAvaliacoes().add(avaliacao);

        // Recalcular nota média
        if (!curso.getAvaliacoes().isEmpty()) {
            Double notaMedia = curso.getAvaliacoes().stream()
                    .mapToDouble(CursoMongo.Avaliacao::getNota)
                    .average()
                    .orElse(0.0);
            curso.setNotaMedia(Math.round(notaMedia * 10.0) / 10.0);
        }

        curso.setDataModificacao(LocalDateTime.now());
        return repository.save(curso);
    }

    /**
     * Incrementa popularidade de um curso
     */
    public CursoMongo incrementarPopularidade(String cursoId, Integer quantidade) {
        CursoMongo curso = obterPorIdOuLancarExcecao(cursoId);
        curso.setPopularidade(curso.getPopularidade() + quantidade);
        curso.setDataModificacao(LocalDateTime.now());
        return repository.save(curso);
    }

    // ==================== DELETE ====================

    /**
     * Delete físico - remove o documento
     */
    public void deletar(String id) {
        repository.deleteById(id);
    }

    /**
     * Delete lógico - marca como inativo
     */
    public CursoMongo desativar(String id) {
        CursoMongo curso = obterPorIdOuLancarExcecao(id);
        curso.setAtivo(false);
        curso.setDataModificacao(LocalDateTime.now());
        return repository.save(curso);
    }

    /**
     * Reativa um curso desativado
     */
    public CursoMongo reativar(String id) {
        CursoMongo curso = obterPorIdOuLancarExcecao(id);
        curso.setAtivo(true);
        curso.setDataModificacao(LocalDateTime.now());
        return repository.save(curso);
    }

    /**
     * Deleta múltiplos cursos
     */
    public void deletarMultiplos(List<String> ids) {
        ids.forEach(repository::deleteById);
    }

    // ==================== ANALYTICS ====================

    /**
     * Retorna estatísticas gerais
     */
    public Map<String, Object> obterEstatisticas() {
        List<CursoMongo> cursos = obterTodos();
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalCursos", cursos.size());
        stats.put("cursosAtivos", cursos.stream().filter(CursoMongo::getAtivo).count());
        stats.put("notaMediaGeral", cursos.stream()
                .mapToDouble(CursoMongo::getNotaMedia)
                .average()
                .orElse(0.0));
        stats.put("totalMatriculados", cursos.stream()
                .mapToInt(CursoMongo::getMatriculados)
                .sum());
        stats.put("cursosComCertificado", cursos.stream()
                .filter(CursoMongo::getCertificadoDisponivel)
                .count());
        
        return stats;
    }

    /**
     * Retorna total de cursos
     */
    public Long contarTotal() {
        return repository.count();
    }
}
