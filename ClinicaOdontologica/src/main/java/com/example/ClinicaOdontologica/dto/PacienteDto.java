package com.example.ClinicaOdontologica.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PacienteDto {
    private Long id;
    private String nombre;
    private String apellido;
    private DomicilioDto domicilio;

    public PacienteDto(Long id, String nombre, String apellido, DomicilioDto domicilio) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.domicilio = domicilio;
    }

    public PacienteDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public DomicilioDto getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(DomicilioDto domicilio) {
        this.domicilio = domicilio;
    }
}
