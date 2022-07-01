package com.example.ClinicaOdontologica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Domicilio {
    @Id
    @SequenceGenerator(name = "domicilio_sequence", sequenceName = "domicilio_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "domicilio_sequence")
    private Long id;
    private String calle;
    private Integer numero;
    private String localidad;
    private String provincia;
    // Pueden haber múltiples pacientes viviendo en una misma dirección
    @OneToMany(mappedBy = "domicilio")
    @JsonIgnore
    private Set<Paciente> pacientes;
    private Boolean deleted = false;

    public Domicilio(Long id, String calle, Integer numero, String localidad, String provincia, Set<Paciente> pacientes) {
        this.id = id;
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
        this.pacientes = pacientes;
    }

    public Domicilio() {
    }

    public Long getId() {
        return id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public Set<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
