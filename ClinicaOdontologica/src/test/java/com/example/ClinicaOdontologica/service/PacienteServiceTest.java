package com.example.ClinicaOdontologica.service;

import com.example.ClinicaOdontologica.model.Domicilio;
import com.example.ClinicaOdontologica.model.Paciente;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PacienteServiceTest {
    @Autowired
    private PacienteService pacienteService;

    @Before
    public void loadData() {
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

        /*-----------------*/

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
    }

    @Test
    public void listPacientes() {
        List<Paciente> pacientes = pacienteService.findAllPacientes();
        Assert.assertTrue(pacientes.size() >= 1);
    }

    @Test
    public void createPaciente() {
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Rivadavia");
        domicilio.setLocalidad("Capital Federal");
        domicilio.setProvincia("Bs As");
        domicilio.setNumero(777);

        Paciente paciente = new Paciente();
        paciente.setNombre("Enzo");
        paciente.setApellido("Fernández");
        paciente.setDomicilio(domicilio);
        paciente.setEmail("prueba@yahoo.com.ar");
        paciente.setFechaEntrada(LocalDate.of(2022, 1, 25));
        paciente.setDni("74747474");

        Paciente pacienteCreado = pacienteService.createPaciente(paciente);
        Assert.assertTrue(paciente.getApellido() == pacienteCreado.getApellido() && paciente.getNombre() == pacienteCreado.getNombre() && paciente.getEmail() == pacienteCreado.getEmail() && paciente.getDni() == pacienteCreado.getDni() && paciente.getDomicilio().getCalle() == pacienteCreado.getDomicilio().getCalle() && paciente.getDomicilio().getNumero() == pacienteCreado.getDomicilio().getNumero() && paciente.getDomicilio().getLocalidad() == pacienteCreado.getDomicilio().getLocalidad() && paciente.getDomicilio().getProvincia() == pacienteCreado.getDomicilio().getProvincia());
    }
}