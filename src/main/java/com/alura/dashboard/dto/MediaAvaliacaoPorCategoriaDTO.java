package com.alura.dashboard.dto;

public class MediaAvaliacaoPorCategoriaDTO {
    private String categoria;
    private Double mediaAvaliacao;

    public MediaAvaliacaoPorCategoriaDTO() {
    }

    public MediaAvaliacaoPorCategoriaDTO(String categoria, Double mediaAvaliacao) {
        this.categoria = categoria;
        this.mediaAvaliacao = mediaAvaliacao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getMediaAvaliacao() {
        return mediaAvaliacao;
    }

    public void setMediaAvaliacao(Double mediaAvaliacao) {
        this.mediaAvaliacao = mediaAvaliacao;
    }
}
