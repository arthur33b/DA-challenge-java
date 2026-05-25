package com.alura.dashboard.model.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Modelo de Curso para MongoDB
 * Utiliza documento desnormalizado com embedding de Categoria e Instrutor
 */
@Document(collection = "cursos")
public class CursoMongo {

    @Id
    private String id;

    @Field("nome")
    private String nome;

    @Field("descricao")
    private String descricao;

    @Field("popularidade")
    private Integer popularidade;

    @Field("notaMedia")
    private Double notaMedia;

    @Field("dataModificacao")
    private LocalDateTime dataModificacao;

    @Field("ativo")
    private Boolean ativo;

    @Field("categoria")
    private CategoriaMongo categoria;

    @Field("instrutor")
    private InstrutorMongo instrutor;

    @Field("avaliacoes")
    private List<Avaliacao> avaliacoes;

    @Field("modulos")
    private List<Modulo> modulos;

    @Field("tags")
    private List<String> tags;

    @Field("matriculados")
    private Integer matriculados;

    @Field("certificadoDisponivel")
    private Boolean certificadoDisponivel;

    // Construtores
    public CursoMongo() {
        this.ativo = true;
        this.dataModificacao = LocalDateTime.now();
    }

    public CursoMongo(String nome, String descricao, Integer popularidade, Double notaMedia,
                      CategoriaMongo categoria, InstrutorMongo instrutor) {
        this();
        this.nome = nome;
        this.descricao = descricao;
        this.popularidade = popularidade;
        this.notaMedia = notaMedia;
        this.categoria = categoria;
        this.instrutor = instrutor;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getPopularidade() {
        return popularidade;
    }

    public void setPopularidade(Integer popularidade) {
        this.popularidade = popularidade;
    }

    public Double getNotaMedia() {
        return notaMedia;
    }

    public void setNotaMedia(Double notaMedia) {
        this.notaMedia = notaMedia;
    }

    public LocalDateTime getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(LocalDateTime dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public CategoriaMongo getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaMongo categoria) {
        this.categoria = categoria;
    }

    public InstrutorMongo getInstrutor() {
        return instrutor;
    }

    public void setInstrutor(InstrutorMongo instrutor) {
        this.instrutor = instrutor;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public List<Modulo> getModulos() {
        return modulos;
    }

    public void setModulos(List<Modulo> modulos) {
        this.modulos = modulos;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Integer getMatriculados() {
        return matriculados;
    }

    public void setMatriculados(Integer matriculados) {
        this.matriculados = matriculados;
    }

    public Boolean getCertificadoDisponivel() {
        return certificadoDisponivel;
    }

    public void setCertificadoDisponivel(Boolean certificadoDisponivel) {
        this.certificadoDisponivel = certificadoDisponivel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CursoMongo that = (CursoMongo) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CursoMongo{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", popularidade=" + popularidade +
                ", notaMedia=" + notaMedia +
                '}';
    }

    // Classe Aninhada: Categoria
    public static class CategoriaMongo {
        private String id;
        private String nome;
        private String descricao;

        public CategoriaMongo() {
        }

        public CategoriaMongo(String id, String nome, String descricao) {
            this.id = id;
            this.nome = nome;
            this.descricao = descricao;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }
    }

    // Classe Aninhada: Instrutor
    public static class InstrutorMongo {
        private String id;
        private String nome;
        private String email;
        private String especializacao;
        private Integer experienciaAnos;

        public InstrutorMongo() {
        }

        public InstrutorMongo(String id, String nome, String email, String especializacao, Integer experienciaAnos) {
            this.id = id;
            this.nome = nome;
            this.email = email;
            this.especializacao = especializacao;
            this.experienciaAnos = experienciaAnos;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getEspecializacao() {
            return especializacao;
        }

        public void setEspecializacao(String especializacao) {
            this.especializacao = especializacao;
        }

        public Integer getExperienciaAnos() {
            return experienciaAnos;
        }

        public void setExperienciaAnos(Integer experienciaAnos) {
            this.experienciaAnos = experienciaAnos;
        }
    }

    // Classe Aninhada: Avaliação
    public static class Avaliacao {
        private String usuario;
        private Double nota;
        private String comentario;
        private LocalDateTime data;

        public Avaliacao() {
        }

        public Avaliacao(String usuario, Double nota, String comentario) {
            this.usuario = usuario;
            this.nota = nota;
            this.comentario = comentario;
            this.data = LocalDateTime.now();
        }

        public String getUsuario() {
            return usuario;
        }

        public void setUsuario(String usuario) {
            this.usuario = usuario;
        }

        public Double getNota() {
            return nota;
        }

        public void setNota(Double nota) {
            this.nota = nota;
        }

        public String getComentario() {
            return comentario;
        }

        public void setComentario(String comentario) {
            this.comentario = comentario;
        }

        public LocalDateTime getData() {
            return data;
        }

        public void setData(LocalDateTime data) {
            this.data = data;
        }
    }

    // Classe Aninhada: Módulo
    public static class Modulo {
        private String titulo;
        private Integer duracao;
        private String videoUrl;
        private String conteudo;

        public Modulo() {
        }

        public Modulo(String titulo, Integer duracao, String videoUrl) {
            this.titulo = titulo;
            this.duracao = duracao;
            this.videoUrl = videoUrl;
        }

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public Integer getDuracao() {
            return duracao;
        }

        public void setDuracao(Integer duracao) {
            this.duracao = duracao;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getConteudo() {
            return conteudo;
        }

        public void setConteudo(String conteudo) {
            this.conteudo = conteudo;
        }
    }
}
