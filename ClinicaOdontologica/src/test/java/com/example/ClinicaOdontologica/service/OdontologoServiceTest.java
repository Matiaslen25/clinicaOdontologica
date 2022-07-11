package com.example.ClinicaOdontologica.service;

import com.example.ClinicaOdontologica.model.Odontologo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
public class OdontologoServiceTest {
    @Autowired
    private OdontologoService odontologoService;

    @Before
    public void loadData() {
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
    }

    @Test
    public void listOdontologos() {
        List<Odontologo> odontologos = odontologoService.findAllOdontologos();
        Assert.assertTrue(odontologos.size() >= 1);
    }

    @Test
    public void createOdontologo() {
        Odontologo odontologo3 = new Odontologo();
        odontologo3.setNombre("Odontologa");
        odontologo3.setApellido("Prueba");
        odontologo3.setMatricula("GHI789");

        Odontologo odontologoCreado3 = odontologoService.createOdontologo(odontologo3);

        Assert.assertTrue(odontologo3.getApellido() == odontologoCreado3.getApellido() && odontologo3.getNombre() == odontologoCreado3.getNombre() && odontologo3.getMatricula() == odontologoCreado3.getMatricula());
    }

    @Test
    public void updateOdontologo() {
        Odontologo odontologo4 = new Odontologo();
        odontologo4.setNombre("Odontologuito");
        odontologo4.setApellido("Perez");
        odontologo4.setMatricula("LLL111");

        Odontologo odontologoCreado4 = odontologoService.createOdontologo(odontologo4);

        odontologoCreado4.setNombre("Cambio");
        odontologoCreado4.setApellido("Cambiado");
        odontologoCreado4.setMatricula("Camb");

        Odontologo odontologo4Actualizado = odontologoService.updateById(odontologoCreado4);

        Assert.assertNotEquals(odontologo4.getApellido(), odontologo4Actualizado.getApellido());
        Assert.assertNotEquals(odontologo4.getNombre(), odontologo4Actualizado.getNombre());
        Assert.assertNotEquals(odontologo4.getMatricula(), odontologo4Actualizado.getMatricula());
        Assert.assertEquals(odontologo4.getId(), odontologo4Actualizado.getId());
    }

    @Test
    public void deleteOdontologo() {
        odontologoService.softDeleteOdontologoById(1L);

        Odontologo odontologo = odontologoService.findOdontologoById(1L);
        Assert.assertNull(odontologo);
    }

    @Test
    public void restoreOdontologo() {
        odontologoService.softDeleteOdontologoById(2L);

        Odontologo odontologo = odontologoService.findOdontologoById(2L);
        Assert.assertNull(odontologo);

        odontologoService.restoreOdontologoById(2L);
        Odontologo odontologoRestaurado = odontologoService.findOdontologoById(2L);
        Assert.assertNotNull(odontologoRestaurado);
    }
}