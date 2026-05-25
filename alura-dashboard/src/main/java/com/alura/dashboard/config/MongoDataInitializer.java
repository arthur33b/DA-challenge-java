package com.alura.dashboard.config;

import com.alura.dashboard.model.mongodb.CursoMongo;
import com.alura.dashboard.repository.mongodb.CursoMongoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Inicializador de dados para MongoDB
 * Cria 10 documentos de cursos com mais de 10 atributos cada
 */
@Configuration
@Profile("mongodb")
public class MongoDataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(MongoDataInitializer.class);

    @Bean
    public CommandLineRunner initMongoData(CursoMongoRepository repository) {
        return args -> {
            // Limpa dados existentes (opcional - comente se não quiser limpar)
            long count = repository.count();
            logger.info("Total de cursos existentes: {}", count);
            
            if (count == 0) {
                logger.info("Iniciando carga de dados no MongoDB...");
                
                List<CursoMongo> cursos = criarCursos();
                repository.saveAll(cursos);
                
                logger.info("✓ {} cursos inseridos com sucesso no MongoDB!", cursos.size());
            } else {
                logger.info("Dados já existem no MongoDB. Pulando inicialização.");
            }
        };
    }

    private List<CursoMongo> criarCursos() {
        List<CursoMongo> cursos = new ArrayList<>();

        // Curso 1: Java Avançado
        CursoMongo curso1 = new CursoMongo();
        curso1.setNome("Java Avançado - Spring Boot Masterclass");
        curso1.setDescricao("Aprenda os conceitos avançados de Java com foco em Spring Boot e arquitetura de microserviços");
        curso1.setPopularidade(2500);
        curso1.setNotaMedia(4.9);
        curso1.setDataModificacao(LocalDateTime.of(2024, 1, 15, 10, 30));
        curso1.setAtivo(true);
        curso1.setMatriculados(2500);
        curso1.setCertificadoDisponivel(true);
        
        CursoMongo.CategoriaMongo cat1 = new CursoMongo.CategoriaMongo();
        cat1.setId("6507f1f77bcf86cd799439001");
        cat1.setNome("Backend");
        cat1.setDescricao("Desenvolvimento voltado para servidor");
        curso1.setCategoria(cat1);
        
        CursoMongo.InstrutorMongo inst1 = new CursoMongo.InstrutorMongo();
        inst1.setId("6507f1f77bcf86cd799439101");
        inst1.setNome("Arthur Borges");
        inst1.setEmail("arthur.borges@alura.com");
        inst1.setEspecializacao("Java Enterprise e Spring Framework");
        inst1.setExperienciaAnos(10);
        curso1.setInstrutor(inst1);
        
        curso1.setTags(Arrays.asList("java", "spring-boot", "backend", "microserviços", "oop"));
        
        List<CursoMongo.Avaliacao> aval1 = new ArrayList<>();
        CursoMongo.Avaliacao av1 = new CursoMongo.Avaliacao();
        av1.setUsuario("joao_silva");
        av1.setNota(5.0);
        av1.setComentario("Excelente curso, muito bem estruturado e didático");
        av1.setData(LocalDateTime.of(2024, 1, 10, 14, 22));
        aval1.add(av1);
        
        CursoMongo.Avaliacao av2 = new CursoMongo.Avaliacao();
        av2.setUsuario("maria_santos");
        av2.setNota(4.8);
        av2.setComentario("Conteúdo avançado, recomendo!");
        av2.setData(LocalDateTime.of(2024, 1, 12, 9, 15));
        aval1.add(av2);
        curso1.setAvaliacoes(aval1);
        
        List<CursoMongo.Modulo> mod1 = new ArrayList<>();
        CursoMongo.Modulo m1 = new CursoMongo.Modulo();
        m1.setTitulo("Fundamentos de Spring Boot");
        m1.setDuracao(180);
        m1.setVideoUrl("https://videos.alura.com/spring-boot-1");
        m1.setConteudo("Introdução ao Spring Boot, dependências e configuração");
        mod1.add(m1);
        
        CursoMongo.Modulo m2 = new CursoMongo.Modulo();
        m2.setTitulo("Arquitetura de Microserviços");
        m2.setDuracao(240);
        m2.setVideoUrl("https://videos.alura.com/spring-boot-2");
        m2.setConteudo("Design de microserviços, comunicação entre serviços");
        mod1.add(m2);
        curso1.setModulos(mod1);
        
        cursos.add(curso1);

        // Curso 2: React
        CursoMongo curso2 = new CursoMongo();
        curso2.setNome("React - Desenvolvimento de Interfaces Modernas");
        curso2.setDescricao("Domine React.js e crie interfaces web dinâmicas e responsivas com as melhores práticas");
        curso2.setPopularidade(1800);
        curso2.setNotaMedia(4.7);
        curso2.setDataModificacao(LocalDateTime.of(2024, 1, 14, 14, 20));
        curso2.setAtivo(true);
        curso2.setMatriculados(1800);
        curso2.setCertificadoDisponivel(true);
        
        CursoMongo.CategoriaMongo cat2 = new CursoMongo.CategoriaMongo();
        cat2.setId("6507f1f77bcf86cd799439002");
        cat2.setNome("Frontend");
        cat2.setDescricao("Desenvolvimento de interfaces web");
        curso2.setCategoria(cat2);
        
        CursoMongo.InstrutorMongo inst2 = new CursoMongo.InstrutorMongo();
        inst2.setId("6507f1f77bcf86cd799439102");
        inst2.setNome("Ana Silva");
        inst2.setEmail("ana.silva@alura.com");
        inst2.setEspecializacao("React, Vue.js e Web Design");
        inst2.setExperienciaAnos(8);
        curso2.setInstrutor(inst2);
        
        curso2.setTags(Arrays.asList("react", "javascript", "frontend", "web", "ui"));
        curso2.setAvaliacoes(Arrays.asList());
        curso2.setModulos(Arrays.asList());
        
        cursos.add(curso2);

        // Curso 3: Python Data Science
        CursoMongo curso3 = new CursoMongo();
        curso3.setNome("Python para Data Science e Machine Learning");
        curso3.setDescricao("Aprenda Python com foco em análise de dados, visualização e algoritmos de machine learning");
        curso3.setPopularidade(2100);
        curso3.setNotaMedia(4.8);
        curso3.setDataModificacao(LocalDateTime.of(2024, 1, 13, 8, 45));
        curso3.setAtivo(true);
        curso3.setMatriculados(2100);
        curso3.setCertificadoDisponivel(true);
        
        CursoMongo.CategoriaMongo cat3 = new CursoMongo.CategoriaMongo();
        cat3.setId("6507f1f77bcf86cd799439003");
        cat3.setNome("Data Science");
        cat3.setDescricao("Análise de dados e inteligência artificial");
        curso3.setCategoria(cat3);
        
        CursoMongo.InstrutorMongo inst3 = new CursoMongo.InstrutorMongo();
        inst3.setId("6507f1f77bcf86cd799439103");
        inst3.setNome("Dr. Roberto Cunha");
        inst3.setEmail("roberto.cunha@alura.com");
        inst3.setEspecializacao("Data Science, IA e Python");
        inst3.setExperienciaAnos(12);
        curso3.setInstrutor(inst3);
        
        curso3.setTags(Arrays.asList("python", "data-science", "machine-learning", "pandas", "sklearn"));
        curso3.setAvaliacoes(Arrays.asList());
        curso3.setModulos(Arrays.asList());
        
        cursos.add(curso3);

        // Curso 4: Docker
        CursoMongo curso4 = new CursoMongo();
        curso4.setNome("Docker - Containerização e DevOps");
        curso4.setDescricao("Domine Docker para containerizar aplicações e melhorar pipeline de deploy em produção");
        curso4.setPopularidade(1400);
        curso4.setNotaMedia(4.6);
        curso4.setDataModificacao(LocalDateTime.of(2024, 1, 12, 19, 15));
        curso4.setAtivo(true);
        curso4.setMatriculados(1400);
        curso4.setCertificadoDisponivel(true);
        
        CursoMongo.CategoriaMongo cat4 = new CursoMongo.CategoriaMongo();
        cat4.setId("6507f1f77bcf86cd799439004");
        cat4.setNome("DevOps");
        cat4.setDescricao("Operações e infraestrutura de software");
        curso4.setCategoria(cat4);
        
        CursoMongo.InstrutorMongo inst4 = new CursoMongo.InstrutorMongo();
        inst4.setId("6507f1f77bcf86cd799439104");
        inst4.setNome("Gustavo Ramos");
        inst4.setEmail("gustavo.ramos@alura.com");
        inst4.setEspecializacao("Docker, Kubernetes e CI/CD");
        inst4.setExperienciaAnos(9);
        curso4.setInstrutor(inst4);
        
        curso4.setTags(Arrays.asList("docker", "devops", "containerização", "kubernetes", "ci-cd"));
        curso4.setAvaliacoes(Arrays.asList());
        curso4.setModulos(Arrays.asList());
        
        cursos.add(curso4);

        // Curso 5: SQL Avançado
        CursoMongo curso5 = new CursoMongo();
        curso5.setNome("SQL Avançado e Otimização de Queries");
        curso5.setDescricao("Aprenda SQL avançado com foco em performance, índices e otimização de consultas complexas");
        curso5.setPopularidade(1600);
        curso5.setNotaMedia(4.5);
        curso5.setDataModificacao(LocalDateTime.of(2024, 1, 11, 16, 30));
        curso5.setAtivo(true);
        curso5.setMatriculados(1600);
        curso5.setCertificadoDisponivel(true);
        
        CursoMongo.CategoriaMongo cat5 = new CursoMongo.CategoriaMongo();
        cat5.setId("6507f1f77bcf86cd799439005");
        cat5.setNome("Banco de Dados");
        cat5.setDescricao("SQL e gestão de dados");
        curso5.setCategoria(cat5);
        
        CursoMongo.InstrutorMongo inst5 = new CursoMongo.InstrutorMongo();
        inst5.setId("6507f1f77bcf86cd799439105");
        inst5.setNome("Fernanda Costa");
        inst5.setEmail("fernanda.costa@alura.com");
        inst5.setEspecializacao("SQL, Oracle e PostgreSQL");
        inst5.setExperienciaAnos(11);
        curso5.setInstrutor(inst5);
        
        curso5.setTags(Arrays.asList("sql", "database", "performance", "oracle", "postgresql"));
        curso5.setAvaliacoes(Arrays.asList());
        curso5.setModulos(Arrays.asList());
        
        cursos.add(curso5);

        // Curso 6: TypeScript
        CursoMongo curso6 = new CursoMongo();
        curso6.setNome("TypeScript - Type Safety em JavaScript");
        curso6.setDescricao("Domine TypeScript e aprenda a escrever código JavaScript seguro e escalável com tipos");
        curso6.setPopularidade(1200);
        curso6.setNotaMedia(4.4);
        curso6.setDataModificacao(LocalDateTime.of(2024, 1, 10, 11, 0));
        curso6.setAtivo(true);
        curso6.setMatriculados(1200);
        curso6.setCertificadoDisponivel(true);
        
        curso6.setCategoria(cat2); // Frontend
        
        CursoMongo.InstrutorMongo inst6 = new CursoMongo.InstrutorMongo();
        inst6.setId("6507f1f77bcf86cd799439106");
        inst6.setNome("Lucas Mendes");
        inst6.setEmail("lucas.mendes@alura.com");
        inst6.setEspecializacao("TypeScript, Angular e Node.js");
        inst6.setExperienciaAnos(7);
        curso6.setInstrutor(inst6);
        
        curso6.setTags(Arrays.asList("typescript", "javascript", "type-safety", "nodejs", "angular"));
        curso6.setAvaliacoes(Arrays.asList());
        curso6.setModulos(Arrays.asList());
        
        cursos.add(curso6);

        // Curso 7: AWS
        CursoMongo curso7 = new CursoMongo();
        curso7.setNome("AWS - Cloud Computing e Infraestrutura");
        curso7.setDescricao("Aprenda a construir, implantar e escalar aplicações na nuvem com Amazon Web Services");
        curso7.setPopularidade(1700);
        curso7.setNotaMedia(4.6);
        curso7.setDataModificacao(LocalDateTime.of(2024, 1, 9, 13, 20));
        curso7.setAtivo(true);
        curso7.setMatriculados(1700);
        curso7.setCertificadoDisponivel(true);
        
        curso7.setCategoria(cat4); // DevOps
        
        CursoMongo.InstrutorMongo inst7 = new CursoMongo.InstrutorMongo();
        inst7.setId("6507f1f77bcf86cd799439107");
        inst7.setNome("Rafael Santos");
        inst7.setEmail("rafael.santos@alura.com");
        inst7.setEspecializacao("AWS, Azure e Google Cloud");
        inst7.setExperienciaAnos(10);
        curso7.setInstrutor(inst7);
        
        curso7.setTags(Arrays.asList("aws", "cloud", "ec2", "s3", "lambda"));
        curso7.setAvaliacoes(Arrays.asList());
        curso7.setModulos(Arrays.asList());
        
        cursos.add(curso7);

        // Curso 8: Git
        CursoMongo curso8 = new CursoMongo();
        curso8.setNome("Git e Controle de Versão - Essencial para Desenvolvimento");
        curso8.setDescricao("Domine Git e aprenda as melhores práticas de versionamento de código para trabalho em equipe");
        curso8.setPopularidade(900);
        curso8.setNotaMedia(4.3);
        curso8.setDataModificacao(LocalDateTime.of(2024, 1, 8, 10, 45));
        curso8.setAtivo(true);
        curso8.setMatriculados(900);
        curso8.setCertificadoDisponivel(true);
        
        CursoMongo.CategoriaMongo cat6 = new CursoMongo.CategoriaMongo();
        cat6.setId("6507f1f77bcf86cd799439006");
        cat6.setNome("Ferramentas");
        cat6.setDescricao("Ferramentas essenciais para desenvolvimento");
        curso8.setCategoria(cat6);
        
        CursoMongo.InstrutorMongo inst8 = new CursoMongo.InstrutorMongo();
        inst8.setId("6507f1f77bcf86cd799439108");
        inst8.setNome("Isabela Moura");
        inst8.setEmail("isabela.moura@alura.com");
        inst8.setEspecializacao("Git, GitHub e GitLab");
        inst8.setExperienciaAnos(6);
        curso8.setInstrutor(inst8);
        
        curso8.setTags(Arrays.asList("git", "github", "versionamento", "colaboração", "scm"));
        curso8.setAvaliacoes(Arrays.asList());
        curso8.setModulos(Arrays.asList());
        
        cursos.add(curso8);

        // Curso 9: REST APIs
        CursoMongo curso9 = new CursoMongo();
        curso9.setNome("REST APIs - Design e Implementação Profissional");
        curso9.setDescricao("Crie APIs REST profissionais seguindo os padrões e melhores práticas da indústria");
        curso9.setPopularidade(2000);
        curso9.setNotaMedia(4.7);
        curso9.setDataModificacao(LocalDateTime.of(2024, 1, 7, 15, 50));
        curso9.setAtivo(true);
        curso9.setMatriculados(2000);
        curso9.setCertificadoDisponivel(true);
        
        curso9.setCategoria(cat1); // Backend
        
        CursoMongo.InstrutorMongo inst9 = new CursoMongo.InstrutorMongo();
        inst9.setId("6507f1f77bcf86cd799439109");
        inst9.setNome("Mariano Costa");
        inst9.setEmail("mariano.costa@alura.com");
        inst9.setEspecializacao("REST APIs, HTTP e Web Services");
        inst9.setExperienciaAnos(9);
        curso9.setInstrutor(inst9);
        
        curso9.setTags(Arrays.asList("rest-api", "http", "json", "backend", "web-services"));
        curso9.setAvaliacoes(Arrays.asList());
        curso9.setModulos(Arrays.asList());
        
        cursos.add(curso9);

        // Curso 10: Segurança Web
        CursoMongo curso10 = new CursoMongo();
        curso10.setNome("Segurança em Aplicações Web - Proteção Contra Vulnerabilidades");
        curso10.setDescricao("Aprenda a identificar, prevenir e corrigir vulnerabilidades comuns em aplicações web");
        curso10.setPopularidade(1100);
        curso10.setNotaMedia(4.5);
        curso10.setDataModificacao(LocalDateTime.of(2024, 1, 6, 9, 20));
        curso10.setAtivo(true);
        curso10.setMatriculados(1100);
        curso10.setCertificadoDisponivel(true);
        
        CursoMongo.CategoriaMongo cat7 = new CursoMongo.CategoriaMongo();
        cat7.setId("6507f1f77bcf86cd799439007");
        cat7.setNome("Segurança");
        cat7.setDescricao("Segurança e proteção de dados");
        curso10.setCategoria(cat7);
        
        CursoMongo.InstrutorMongo inst10 = new CursoMongo.InstrutorMongo();
        inst10.setId("6507f1f77bcf86cd799439110");
        inst10.setNome("Viktor Sokolov");
        inst10.setEmail("viktor.sokolov@alura.com");
        inst10.setEspecializacao("Segurança da Informação e Criptografia");
        inst10.setExperienciaAnos(14);
        curso10.setInstrutor(inst10);
        
        curso10.setTags(Arrays.asList("segurança", "web-security", "owasp", "criptografia", "autenticação"));
        curso10.setAvaliacoes(Arrays.asList());
        curso10.setModulos(Arrays.asList());
        
        cursos.add(curso10);

        return cursos;
    }
}
