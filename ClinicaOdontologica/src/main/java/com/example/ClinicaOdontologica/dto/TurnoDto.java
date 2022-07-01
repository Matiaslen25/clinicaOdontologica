package com.example.ClinicaOdontologica.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TurnoDto {
    private Long id;
    private LocalDateTime fechaHora;
    private PacienteDto paciente;
    private OdontologoDto odontologo;

    public TurnoDto(Long id, LocalDateTime fechaHora, PacienteDto paciente, OdontologoDto odontologo) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.paciente = paciente;
        this.odontologo = odontologo;
    }

    public TurnoDto() {
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

    public PacienteDto getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDto paciente) {
        this.paciente = paciente;
    }

    public OdontologoDto getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(OdontologoDto odontologo) {
        this.odontologo = odontologo;
    }
}
