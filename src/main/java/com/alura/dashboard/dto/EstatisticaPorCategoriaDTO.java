package com.alura.dashboard.dto;

public class EstatisticaPorCategoriaDTO {
    private String categoria;
    private Long quantidadeCursos;

    public EstatisticaPorCategoriaDTO(String categoria, Long quantidadeCursos) {
        this.categoria = categoria;
        this.quantidadeCursos = quantidadeCursos;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Long getQuantidadeCursos() {
        return quantidadeCursos;
    }

    public void setQuantidadeCursos(Long quantidadeCursos) {
        this.quantidadeCursos = quantidadeCursos;
    }
}