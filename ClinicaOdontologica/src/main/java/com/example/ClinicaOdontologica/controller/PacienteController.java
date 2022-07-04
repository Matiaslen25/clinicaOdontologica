package com.example.ClinicaOdontologica.controller;

import com.example.ClinicaOdontologica.model.Paciente;
import com.example.ClinicaOdontologica.service.PacienteService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    @GetMapping(path = "/pacientes")
    public ResponseEntity getAll() throws JsonProcessingException {
        List<Paciente> pacientes = pacienteService.findAllFilteringDeleted(false);
        if (pacientes == null) {
            return new ResponseEntity("No hay pacientes registrados", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(pacienteService.entityListToDtoList(pacientes), HttpStatus.OK);
            // return new ResponseEntity(pacientes, HttpStatus.OK);
        }
    }

    @GetMapping(path = "/pacientes/deleted")
    public ResponseEntity getAllDeleted() throws JsonProcessingException {
        List<Paciente> pacientes = pacienteService.findAllFilteringDeleted(true);
        if (pacientes == null) {
            return new ResponseEntity("No se encontraron pacientes eliminados", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(pacienteService.entityListToDtoList(pacientes), HttpStatus.OK);
            // return new ResponseEntity(pacientes, HttpStatus.OK);
        }
    }

    @PostMapping(path = "/pacientes")
    public ResponseEntity savePaciente(@RequestBody Paciente paciente) {
        Paciente p = pacienteService.createPaciente(paciente);

        if (p != null) {
            return new ResponseEntity(pacienteService.entityToDto(p), HttpStatus.OK);
            // return new ResponseEntity(p, HttpStatus.OK);
        } else {
            return new ResponseEntity("Ocurrió un error al registrar el paciente", HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping(path = "/pacientes/{id}")
    public ResponseEntity savePacienteById(@PathVariable Long id){
        Paciente paciente = pacienteService.findPacienteById(id);

        if (paciente == null) {
            return new ResponseEntity("Paciente no encontrado", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(pacienteService.entityToDto(paciente), HttpStatus.OK);
            // return new ResponseEntity(paciente, HttpStatus.OK);
        }
    }

    @PutMapping(path = "/pacientes/update")
    public ResponseEntity modifyPacienteById(@RequestBody Paciente paciente) {
        Paciente p = pacienteService.findPacienteById(paciente.getId());

        if (p == null) {
            return new ResponseEntity("Ocurrió un error al modificar el paciente", HttpStatus.BAD_GATEWAY);
        } else {
            p = pacienteService.updateById(paciente);
            return new ResponseEntity(pacienteService.entityToDto(p), HttpStatus.OK);
            // return new ResponseEntity(p, HttpStatus.OK);
        }
    }

    @DeleteMapping(path = "/pacientes/{id}")
    public ResponseEntity deletePacienteById(@PathVariable Long id) {
        Paciente paciente = pacienteService.findPacienteById(id);

        if (pacienteService != null) {
            pacienteService.softDeletePacienteById(id);
            return new ResponseEntity("Paciente eliminado con éxito", HttpStatus.OK);
        } else {
            return new ResponseEntity("Paciente no encontrado", HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping(path = "/pacientes/restore/{id}")
    public ResponseEntity modifyPacienteById(@PathVariable Long id) {
        Paciente p = pacienteService.findPacienteByIdIgnoringDeleted(id);

        if (p == null) {
            return new ResponseEntity("Ocurrió un error al modificar el paciente", HttpStatus.BAD_GATEWAY);
        } else if (p.getDeleted() == false) {
            return new ResponseEntity("El paciente de id " + id + " no fue eliminado", HttpStatus.BAD_REQUEST);
        }  else {
            pacienteService.restorePacienteById(id);
            return new ResponseEntity(pacienteService.entityToDto(p), HttpStatus.OK);
            // return new ResponseEntity("Paciente restaurado con éxito", HttpStatus.OK);
        }
    }
}
