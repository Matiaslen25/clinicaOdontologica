package com.example.ClinicaOdontologica.controller;

import com.example.ClinicaOdontologica.model.Paciente;
import com.example.ClinicaOdontologica.service.PacienteService;
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
public class PacienteController {
    @Autowired
    public PacienteService pacienteService;

    private static final Logger logger = Logger.getLogger(OdontologoController.class);

    @GetMapping(path = "/pacientes")
    public ResponseEntity getAll() throws JsonProcessingException {
        logger.info("Buscando todos los pacientes activos");
        List<Paciente> pacientes = pacienteService.findAllFilteringDeleted(false);
        if (pacientes == null || pacientes.size() < 1) {
            logger.error("No se encontraron pacientes activos");
            return new ResponseEntity("No hay pacientes registrados", HttpStatus.NOT_FOUND);
        } else {
            logger.info("Devolviendo " + pacientes.size() + " pacientes encontrados");
            return new ResponseEntity(pacienteService.entityListToDtoList(pacientes), HttpStatus.OK);
        }
    }

    @GetMapping(path = "/pacientes/deleted")
    public ResponseEntity getAllDeleted() throws JsonProcessingException {
        logger.info("Buscando todos los pacientes eliminados");
        List<Paciente> pacientes = pacienteService.findAllFilteringDeleted(true);
        if (pacientes == null || pacientes.size() < 1) {
            logger.error("No se encontraron pacientes eliminados");
            return new ResponseEntity("No se encontraron pacientes eliminados", HttpStatus.NOT_FOUND);
        } else {
            logger.info("Devolviendo " + pacientes.size() + " pacientes eliminados encontrados");
            return new ResponseEntity(pacienteService.entityListToDtoList(pacientes), HttpStatus.OK);
        }
    }

    @PostMapping(path = "/pacientes")
    public ResponseEntity savePaciente(@RequestBody Paciente paciente) {
        logger.info("Creando paciente");
        Paciente p = pacienteService.createPaciente(paciente);

        if (p != null) {
            logger.info("Paciente "  + p.getNombre() + " "  + p.getApellido() + " creado correctamente con id " + p.getId());
            return new ResponseEntity(pacienteService.entityToDto(p), HttpStatus.OK);
        } else {
            logger.error("Ocurrió un error al crear el paciente");
            return new ResponseEntity("Ocurrió un error al registrar el paciente", HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping(path = "/pacientes/{id}")
    public ResponseEntity savePacienteById(@PathVariable Long id){
        logger.info("Buscando paciente con id " + id);
        Paciente paciente = pacienteService.findPacienteById(id);

        if (paciente == null) {
            logger.error("Paciente con id " + id + " no encontrado");
            return new ResponseEntity("Paciente no encontrado", HttpStatus.NOT_FOUND);
        } else {
            logger.info("Paciente "  + paciente.getNombre() + " "  + paciente.getApellido() + " encontrado");
            return new ResponseEntity(pacienteService.entityToDto(paciente), HttpStatus.OK);
        }
    }

    @PutMapping(path = "/pacientes/update")
    public ResponseEntity modifyPacienteById(@RequestBody Paciente paciente) {
        logger.info("Editando paciente con id " + paciente.getId());
        Paciente p = pacienteService.findPacienteById(paciente.getId());

        if (p == null) {
            logger.error("Ocurrió un error al editar el paciente");
            return new ResponseEntity("Ocurrió un error al modificar el paciente", HttpStatus.BAD_GATEWAY);
        } else {
            p = pacienteService.updateById(paciente);
            logger.info("Paciente editado con éxito");
            return new ResponseEntity(pacienteService.entityToDto(p), HttpStatus.OK);
        }
    }

    @DeleteMapping(path = "/pacientes/{id}")
    public ResponseEntity deletePacienteById(@PathVariable Long id) {
        logger.info("Eliminando paciente con id " + id);
        Paciente paciente = pacienteService.findPacienteById(id);

        if (pacienteService != null) {
            pacienteService.softDeletePacienteById(id);
            logger.info("Paciente con id " + id + " eliminado con éxito");
            return new ResponseEntity("Paciente eliminado con éxito", HttpStatus.OK);
        } else {
            logger.error("Ocurrió un error al eliminar el paciente con id " + id);
            return new ResponseEntity("Paciente no encontrado", HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping(path = "/pacientes/restore/{id}")
    public ResponseEntity modifyPacienteById(@PathVariable Long id) {
        logger.info("Restaurando paciente con id " + id);
        Paciente p = pacienteService.findPacienteByIdIgnoringDeleted(id);

        if (p == null) {
            logger.error("Ocurrió un error al restaurar el paciente con id " + id + ". No fue encontrado");
            return new ResponseEntity("Ocurrió un error al modificar el paciente", HttpStatus.BAD_GATEWAY);
        } else if (p.getDeleted() == false) {
            logger.error("Ocurrió un error al restaurar el paciente con id " + id + ". El paciente no se encuentra eliminado");
            return new ResponseEntity("El paciente de id " + id + " no fue eliminado", HttpStatus.BAD_REQUEST);
        }  else {
            pacienteService.restorePacienteById(id);
            logger.info("Paciente con id " + id + " restaurado con éxito");
            return new ResponseEntity(pacienteService.entityToDto(p), HttpStatus.OK);
        }
    }
}
