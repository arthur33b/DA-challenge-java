package com.alura.dashboard.dto;

import java.time.LocalDateTime;

public class CursoConsultaEventoDTO {
    private Long categoriaId;
    private String tipoConsulta;
    private String usuario;
    private LocalDateTime dataHora;

    public CursoConsultaEventoDTO() {
    }

    public CursoConsultaEventoDTO(Long categoriaId, String tipoConsulta, String usuario, LocalDateTime dataHora) {
        this.categoriaId = categoriaId;
        this.tipoConsulta = tipoConsulta;
        this.usuario = usuario;
        this.dataHora = dataHora;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
