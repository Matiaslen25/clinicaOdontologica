package com.example.ClinicaOdontologica.controller;

import com.example.ClinicaOdontologica.model.Odontologo;
import com.example.ClinicaOdontologica.service.OdontologoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
public class OdontologoController {
    @Autowired
    public OdontologoService odontologoService;

    @GetMapping(path = "/odontologos")
    public ResponseEntity getAll() throws JsonProcessingException {
        List<Odontologo> odontologos = odontologoService.findAllFilteringDeleted(false);
        if (odontologos == null) {
            return new ResponseEntity("No hay odontólogos registrados", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(odontologoService.entityListToDtoList(odontologos), HttpStatus.OK);
            //return new ResponseEntity(odontologos, HttpStatus.OK);
        }
    }

    @GetMapping(path = "/odontologos/deleted")
    public ResponseEntity getAllDeleted() throws JsonProcessingException {
        List<Odontologo> odontologos = odontologoService.findAllFilteringDeleted(true);
        if (odontologos == null) {
            return new ResponseEntity("No se encontraron odontólogos eliminados", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(odontologoService.entityListToDtoList(odontologos), HttpStatus.OK);
            // return new ResponseEntity(odontologos, HttpStatus.OK);
        }
    }

    @PostMapping(path = "/odontologos")
    public ResponseEntity saveOdontologo(@RequestBody Odontologo odontologo) {
        Odontologo o = odontologoService.createOdontologo(odontologo);

        if (o != null) {
            return new ResponseEntity(odontologoService.entityToDto(o), HttpStatus.OK);
            // return new ResponseEntity(o, HttpStatus.OK);
        } else {
            return new ResponseEntity("Ocurrió un error al registrar el odontólogo", HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping(path = "/odontologos/{id}")
    public ResponseEntity findOdontologoById(@PathVariable Long id){
        Odontologo odontologo = odontologoService.findOdontologoById(id);

        if (odontologo == null) {
            return new ResponseEntity("Odontólogo no encontrado", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(odontologoService.entityToDto(odontologo), HttpStatus.OK);
            // new ResponseEntity(odontologo, HttpStatus.OK);
        }
    }

    @PutMapping(path = "/odontologos/update")
    public ResponseEntity modifyOdontologoById(@RequestBody Odontologo odontologo) {
        Odontologo o = odontologoService.findOdontologoById(odontologo.getId());

        if (o == null) {
            return new ResponseEntity("Ocurrió un error al modificar el odontólogo", HttpStatus.BAD_GATEWAY);
        } else {
            o = odontologoService.updateById(odontologo);
            return new ResponseEntity(odontologoService.entityToDto(o), HttpStatus.OK);
            // return new ResponseEntity(o, HttpStatus.OK);
        }
    }

    @DeleteMapping(path = "/odontologos/{id}")
    public ResponseEntity deleteOdontologoById(@PathVariable Long id) {
        Odontologo odontologo = odontologoService.findOdontologoById(id);

        if (odontologo != null) {
            odontologoService.softDeleteOdontologoById(id);
            return new ResponseEntity("Odontólogo eliminado con éxito", HttpStatus.OK);
        } else {
            return new ResponseEntity("Odontólogo no encontrado", HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping(path = "/odontologos/restore/{id}")
    public ResponseEntity restoreOdontologoById(@PathVariable Long id) {
        Odontologo o = odontologoService.findOdontologoByIdIgnoringDeleted(id);

        if (o == null) {
            return new ResponseEntity("Ocurrió un error al modificar el odontólogo", HttpStatus.BAD_GATEWAY);
        } else if(o.getDeleted() == false) {
            return new ResponseEntity("El odontólogo de id " + id + " no fue eliminado", HttpStatus.BAD_REQUEST);
        } else {
                odontologoService.restoreOdontologoById(id);
                return new ResponseEntity(odontologoService.entityToDto(o), HttpStatus.OK);
        }
    }
}