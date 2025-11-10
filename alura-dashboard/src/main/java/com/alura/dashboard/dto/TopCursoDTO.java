package com.alura.dashboard.dto;

public class TopCursoDTO {
    private Long id;
    private String nome;
    private Integer popularidade;
    private Double notaMedia;

    public TopCursoDTO(Long id, String nome, Integer popularidade, Double notaMedia) {
        this.id = id;
        this.nome = nome;
        this.popularidade = popularidade;
        this.notaMedia = notaMedia;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getPopularidade() {
        return popularidade;
    }

    public Double getNotaMedia() {
        return notaMedia;
    }
}