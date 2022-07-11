package com.example.ClinicaOdontologica.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Turno {
    @Id
    @SequenceGenerator(name = "turno_sequence", sequenceName = "turno_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "turno_sequence")
    private Long id;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaHora;
    // Un turno solo puede tener un paciente
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
    // Un turno solo puede tener un odontólogo
    @ManyToOne
    @JoinColumn(name = "odontologo_id")
    private Odontologo odontologo;
    private Boolean deleted = false;

    public Turno(Long id, LocalDateTime fechaHora, Paciente paciente, Odontologo odontologo) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.paciente = paciente;
        this.odontologo = odontologo;
    }

    public Turno() {
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Odontologo getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(Odontologo odontologo) {
        this.odontologo = odontologo;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}