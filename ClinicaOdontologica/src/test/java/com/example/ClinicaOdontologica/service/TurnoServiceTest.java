package com.example.ClinicaOdontologica.service;

import com.example.ClinicaOdontologica.model.Domicilio;
import com.example.ClinicaOdontologica.model.Odontologo;
import com.example.ClinicaOdontologica.model.Paciente;
import com.example.ClinicaOdontologica.model.Turno;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TurnoServiceTest {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;

    @Before
    public void loadData() {
        /* Creando odontólogos */
        Odontologo odontologo1 = new Odontologo();
        odontologo1.setNombre("Odontólogo");
        odontologo1.setApellido("Test");
        odontologo1.setMatricula("ABC123");

        Odontologo odontologo2 = new Odontologo();
        odontologo2.setNombre("Odontologist");
        odontologo2.setApellido("Testing");
        odontologo2.setMatricula("DEF456");

        Odontologo odontologoCreado1 = odontologoService.createOdontologo(odontologo1);
        Odontologo odontologoCreado2 = odontologoService.createOdontologo(odontologo2);
        /* ------------- */
        /* Creando pacientes */
        Domicilio domicilio1 = new Domicilio();
        domicilio1.setCalle("Cabildo");
        domicilio1.setLocalidad("CABA");
        domicilio1.setProvincia("Buenos Aires");
        domicilio1.setNumero(123);

        Paciente paciente1 = new Paciente();
        paciente1.setNombre("Paciente");
        paciente1.setApellido("Test");
        paciente1.setDomicilio(domicilio1);
        paciente1.setDni("12345678");
        paciente1.setEmail("lalala@mail.com");
        paciente1.setFechaEntrada(LocalDate.now());

        /*----*/

        Domicilio domicilio2 = new Domicilio();
        domicilio2.setCalle("Prueba");
        domicilio2.setLocalidad("Rosario");
        domicilio2.setProvincia("Santa Fe");
        domicilio2.setNumero(456);

        Paciente paciente2 = new Paciente();
        paciente2.setNombre("Patient");
        paciente2.setApellido("Testing");
        paciente2.setDomicilio(domicilio2);
        paciente2.setDni("87654321");
        paciente2.setEmail("lelele@gmail.com");
        paciente2.setFechaEntrada(LocalDate.now());

        Paciente pacienteCreado1 = pacienteService.createPaciente(paciente1);
        Paciente pacienteCreado2 = pacienteService.createPaciente(paciente2);
        /* ------------- */
        /* Creando turnos */

        Turno turno1 = new Turno();
        turno1.setOdontologo(odontologoCreado1);
        turno1.setPaciente(pacienteCreado1);
        turno1.setFechaHora(LocalDate.of(2022,12,25).atStartOfDay());

        Turno turno2 = new Turno();
        turno2.setOdontologo(odontologoCreado1);
        turno2.setPaciente(pacienteCreado2);
        turno2.setFechaHora(LocalDateTime.now());

        Turno turno3 = new Turno();
        turno3.setOdontologo(odontologoCreado2);
        turno3.setPaciente(pacienteCreado1);
        turno3.setFechaHora(LocalDate.of(2022,12,25).atStartOfDay());

        Turno turno4 = new Turno();
        turno4.setOdontologo(odontologoCreado2);
        turno4.setPaciente(pacienteCreado2);
        turno4.setFechaHora(LocalDateTime.now());

        Turno turnoCreado1 = turnoService.createTurno(turno1);
        Turno turnoCreado2 = turnoService.createTurno(turno2);
        Turno turnoCreado3 = turnoService.createTurno(turno3);
        Turno turnoCreado4 = turnoService.createTurno(turno4);
        /* ------------- */
    }

    @Test
    public void listTurnos() {
        List<Turno> turnos = turnoService.findAllTurnos();
        Assert.assertTrue(turnos.size() >= 1);
    }

    @Test
    public void getTurnosBetweenDates() {
        LocalDateTime startDate = LocalDate.of(2022,12,23).atStartOfDay();
        LocalDateTime endDate = LocalDate.of(2022,12,26).atStartOfDay();
        List<Turno> turnos = turnoService.filterTurnosBetweenDates(startDate.toLocalDate(), endDate.toLocalDate(), false);
        Assert.assertTrue(turnos.size() >= 2);

        for (Turno t:turnos) {
            Assert.assertTrue(t.getFechaHora().isBefore(endDate) && t.getFechaHora().isAfter(startDate));
        }
    }

    @Test
    public void createTurno() {
        Odontologo odontologo = new Odontologo();
        odontologo.setNombre("Probando");
        odontologo.setApellido("Odonto");
        odontologo.setMatricula("LALALA123");

        Odontologo odontologoCreado = odontologoService.createOdontologo(odontologo);

        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Pruebita");
        domicilio.setLocalidad("TEST");
        domicilio.setProvincia("Proba, Ndo");
        domicilio.setNumero(653);

        Paciente paciente = new Paciente();
        paciente.setNombre("Probando");
        paciente.setApellido("Paci");
        paciente.setDomicilio(domicilio);
        paciente.setDni("564465645");
        paciente.setEmail("verdura@mail.com");
        paciente.setFechaEntrada(LocalDate.now());

        Paciente pacienteCreado = pacienteService.createPaciente(paciente);

        Turno turno = new Turno();
        turno.setOdontologo(odontologoCreado);
        turno.setPaciente(pacienteCreado);
        turno.setFechaHora(LocalDate.of(2022, 1, 25).atStartOfDay());

        Turno turnoCreado = turnoService.createTurno(turno);

        Assert.assertTrue(turnoCreado.getFechaHora() == turno.getFechaHora() && turnoCreado.getOdontologo().getMatricula() == turno.getOdontologo().getMatricula() && turnoCreado.getPaciente().getDni() == turno.getPaciente().getDni());
    }
}