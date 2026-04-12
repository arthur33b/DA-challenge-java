package com.alura.dashboard.dto;

public class CursoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private String categoria;
    private String instrutor;
    private Integer popularidade;
    private Double notaMedia;

    public CursoDTO(Long id, String nome, String descricao, String categoria, String instrutor, Integer popularidade, Double notaMedia) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.instrutor = instrutor;
        this.popularidade = popularidade;
        this.notaMedia = notaMedia;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getInstrutor() {
        return instrutor;
    }

    public Integer getPopularidade() {
        return popularidade;
    }

    public Double getNotaMedia() {
        return notaMedia;
    }
}