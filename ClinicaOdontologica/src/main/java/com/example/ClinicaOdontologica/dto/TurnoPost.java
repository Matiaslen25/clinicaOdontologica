package com.example.ClinicaOdontologica.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TurnoPost {
    private Long id;
    private LocalDateTime fechaHora;
    private Long idOdontologo;
    private Long idPaciente;

    public TurnoPost(Long id, LocalDateTime fechaHora, Long idOdontologo, Long idPaciente) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.idOdontologo = idOdontologo;
        this.idPaciente = idPaciente;
    }

    public TurnoPost() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Long getIdOdontologo() {
        return idOdontologo;
    }

    public void setIdOdontologo(Long idOdontologo) {
        this.idOdontologo = idOdontologo;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }
}
