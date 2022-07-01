package com.example.ClinicaOdontologica.dto;

import java.time.LocalDate;

public class TurnoWithDatesDto {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public TurnoWithDatesDto(LocalDate fechaInicio, LocalDate fechaFin) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public TurnoWithDatesDto() {
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}
