package com.example.ClinicaOdontologica.controller;

import com.example.ClinicaOdontologica.model.Odontologo;
import com.example.ClinicaOdontologica.service.OdontologoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
@RestController
public class OdontologoController {
    @Autowired
    public OdontologoService odontologoService;

    private static final Logger logger = Logger.getLogger(OdontologoController.class);

    @GetMapping(path = "/odontologos")
    public ResponseEntity getAll() throws JsonProcessingException {
        logger.info("Buscando todos los odontólogos activos");
        List<Odontologo> odontologos = odontologoService.findAllFilteringDeleted(false);
        if (odontologos == null || odontologos.size() < 1) {
            logger.error("No se encontraron odontólogos activos");
            return new ResponseEntity("No hay odontólogos registrados", HttpStatus.NOT_FOUND);
        } else {
            logger.info("Devolviendo " + odontologos.size() + " odontólogos encontrados");
            return new ResponseEntity(odontologoService.entityListToDtoList(odontologos), HttpStatus.OK);
        }
    }

    @GetMapping(path = "/odontologos/deleted")
    public ResponseEntity getAllDeleted() throws JsonProcessingException {
        logger.info("Buscando todos los odontólogos eliminados");
        List<Odontologo> odontologos = odontologoService.findAllFilteringDeleted(true);
        if (odontologos == null || odontologos.size() < 1) {
            logger.error("No se encontraron odontólogos eliminados");
            return new ResponseEntity("No se encontraron odontólogos eliminados", HttpStatus.NOT_FOUND);
        } else {
            logger.info("Devolviendo " + odontologos.size() + " odontólogos eliminados encontrados");
            return new ResponseEntity(odontologoService.entityListToDtoList(odontologos), HttpStatus.OK);
        }
    }

    @PostMapping(path = "/odontologos")
    public ResponseEntity saveOdontologo(@RequestBody Odontologo odontologo) {
        logger.info("Creando odontólogo");
        Odontologo o = odontologoService.createOdontologo(odontologo);

        if (o != null) {
            logger.info("Odontólogo "  + o.getNombre() + " "  + o.getApellido() + " creado correctamente con id " + o.getId());
            return new ResponseEntity(odontologoService.entityToDto(o), HttpStatus.OK);
        } else {
            logger.error("Ocurrió un error al crear el odontólogo");
            return new ResponseEntity("Ocurrió un error al registrar el odontólogo", HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping(path = "/odontologos/{id}")
    public ResponseEntity findOdontologoById(@PathVariable Long id){
        logger.info("Buscando odontólogo con id " + id);
        Odontologo odontologo = odontologoService.findOdontologoById(id);

        if (odontologo == null) {
            logger.error("Odontólogo con id " + id + " no encontrado");
            return new ResponseEntity("Odontólogo no encontrado", HttpStatus.NOT_FOUND);
        } else {
            logger.info("Odontólogo "  + odontologo.getNombre() + " "  + odontologo.getApellido() + " encontrado");
            return new ResponseEntity(odontologoService.entityToDto(odontologo), HttpStatus.OK);
        }
    }

    @PutMapping(path = "/odontologos/update")
    public ResponseEntity modifyOdontologoById(@RequestBody Odontologo odontologo) {
        logger.info("Editando odontólogo con id " + odontologo.getId());
        Odontologo o = odontologoService.findOdontologoById(odontologo.getId());

        if (o == null) {
            logger.error("Ocurrió un error al editar el odontólogo");
            return new ResponseEntity("Ocurrió un error al modificar el odontólogo", HttpStatus.BAD_GATEWAY);
        } else {
            o = odontologoService.updateById(odontologo);
            logger.info("Odontólogo editado con éxito");
            return new ResponseEntity(odontologoService.entityToDto(o), HttpStatus.OK);
        }
    }

    @DeleteMapping(path = "/odontologos/{id}")
    public ResponseEntity deleteOdontologoById(@PathVariable Long id) {
        logger.info("Eliminando odontólogo con id " + id);
        Odontologo odontologo = odontologoService.findOdontologoById(id);

        if (odontologo != null) {
            odontologoService.softDeleteOdontologoById(id);
            logger.info("Odontólogo con id " + id + " eliminado con éxito");
            return new ResponseEntity("Odontólogo eliminado con éxito", HttpStatus.OK);
        } else {
            logger.error("Ocurrió un error al eliminar el odontólogo con id " + id);
            return new ResponseEntity("Odontólogo no encontrado", HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping(path = "/odontologos/restore/{id}")
    public ResponseEntity restoreOdontologoById(@PathVariable Long id) {
        logger.info("Restaurando odontólogo con id " + id);
        Odontologo o = odontologoService.findOdontologoByIdIgnoringDeleted(id);

        if (o == null) {
            logger.error("Ocurrió un error al restaurar el odontólogo con id " + id + ". No fue encontrado");
            return new ResponseEntity("Ocurrió un error al restaurar el odontólogo", HttpStatus.BAD_GATEWAY);
        } else if(o.getDeleted() == false) {
            logger.error("Ocurrió un error al restaurar el odontólogo con id " + id + ". El odontólogo no se encuentra eliminado");
            return new ResponseEntity("El odontólogo de id " + id + " no fue eliminado", HttpStatus.BAD_REQUEST);
        } else {
            odontologoService.restoreOdontologoById(id);
            logger.info("Odontólogo con id " + id + " restaurado con éxito");
            return new ResponseEntity(odontologoService.entityToDto(o), HttpStatus.OK);
        }
    }
}