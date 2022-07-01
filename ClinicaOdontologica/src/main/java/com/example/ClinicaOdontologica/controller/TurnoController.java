package com.example.ClinicaOdontologica.controller;

import com.example.ClinicaOdontologica.dto.TurnoPost;
import com.example.ClinicaOdontologica.dto.TurnoWithDatesDto;
import com.example.ClinicaOdontologica.model.Odontologo;
import com.example.ClinicaOdontologica.model.Paciente;
import com.example.ClinicaOdontologica.model.Turno;
import com.example.ClinicaOdontologica.service.OdontologoService;
import com.example.ClinicaOdontologica.service.PacienteService;
import com.example.ClinicaOdontologica.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
public class TurnoController {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/turnos")
    public ResponseEntity getAll(@RequestParam(name="paciente", required = false) Long idPaciente, @RequestParam(name = "odontologo", required = false) Long idOdontologo) {
        List<Turno> turnos;

        if (idOdontologo != null) {
            if (idPaciente != null) {
                // El usuario está buscando turnos por odontólogo y por paciente
                turnos = turnoService.findTurnosByOdontologoAndPaciente(idOdontologo, idPaciente, false);
            } else {
                // El usuario está buscando turnos solo por odontólogo
                turnos = turnoService.findTurnoByOdontologo(idOdontologo, false);
            }
        } else if (idPaciente != null) {
            // El usuario está buscando turnos solo por paciente
            turnos = turnoService.findTurnoByPaciente(idPaciente, false);
        } else {
            // El usuario está buscando todos los turnos -> TO DO: validar permisos super admin?
            turnos = turnoService.findAllFilteringDeleted(false);
        }

        if(turnos == null) {
            return new ResponseEntity("No se encontraron turnos", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(turnoService.entityListToDtoList(turnos), HttpStatus.OK);
        }
    }

    @GetMapping("/turnos/deleted")
    public ResponseEntity getAllDeleted(@RequestParam(name="paciente", required = false) Long idPaciente, @RequestParam(name = "odontologo", required = false) Long idOdontologo) {
        List<Turno> turnos;

        if (idOdontologo != null) {
            if (idPaciente != null) {
                // El usuario está buscando turnos por odontólogo y por paciente
                turnos = turnoService.findTurnosByOdontologoAndPaciente(idOdontologo, idPaciente, true);
            } else {
                // El usuario está buscando turnos solo por odontólogo
                turnos = turnoService.findTurnoByOdontologo(idOdontologo, true);
            }
        } else if (idPaciente != null) {
            // El usuario está buscando turnos solo por paciente
            turnos = turnoService.findTurnoByPaciente(idPaciente, true);
        } else {
            // El usuario está buscando todos los turnos -> TO DO: validar permisos super admin?
            turnos = turnoService.findAllFilteringDeleted(true);
        }

        if(turnos == null) {
            return new ResponseEntity("No se encontraron turnos", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(turnoService.entityListToDtoList(turnos), HttpStatus.OK);
        }
    }

    @GetMapping("/turnos/{id}")
    public ResponseEntity getTurnoById(@PathVariable Long id) {
        Turno turno = turnoService.findTurnoById(id, false);

        if (turno == null) {
            return new ResponseEntity("No se encontró el turno", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity(turnoService.entityToDto(turno), HttpStatus.OK);
        }
    }

    @GetMapping("/turnos/deleted/{id}")
    public ResponseEntity getDeletedTurnoById(@PathVariable Long id) {
        Turno turno = turnoService.findTurnoById(id, true);

        if (turno == null) {
            return new ResponseEntity("No se encontró el turno", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity(turnoService.entityToDto(turno), HttpStatus.OK);
        }
    }

    @PostMapping("/turnos/fechas")
    public ResponseEntity findTurnosBetweenDates(@RequestBody TurnoWithDatesDto dates) {
        if (dates.getFechaInicio() == null || dates.getFechaFin() == null) {
            return new ResponseEntity("Indique fecha de incio y de fin", HttpStatus.BAD_REQUEST);
        } else {
            List<Turno> turnos = turnoService.filterTurnosBetweenDates(dates.getFechaInicio(), dates.getFechaFin(), false);

            if (turnos == null) {
                return new ResponseEntity("No se encontraron turnos", HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity(turnoService.entityListToDtoList(turnos), HttpStatus.OK);
            }
        }
    }

    @PostMapping("/turnos/deleted/fechas")
    public ResponseEntity findDeletedTurnosBetweenDates(@RequestBody  TurnoWithDatesDto dates) {
        if (dates.getFechaInicio() == null || dates.getFechaFin() == null) {
            return new ResponseEntity("Indique fecha de incio y de fin", HttpStatus.BAD_REQUEST);
        } else {
            List<Turno> turnos = turnoService.filterTurnosBetweenDates(dates.getFechaInicio(), dates.getFechaFin(), true);

            if (turnos == null) {
                return new ResponseEntity("No se encontraron turnos", HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity(turnoService.entityListToDtoList(turnos), HttpStatus.OK);
            }
        }
    }

    @PostMapping("/turnos")
    public ResponseEntity saveTurno(@RequestBody TurnoPost turno) {
        Odontologo odontologo = odontologoService.findOdontologoById(turno.getIdOdontologo());
        Paciente paciente = pacienteService.findPacienteById(turno.getIdPaciente());
        if (odontologo == null) {
            return new ResponseEntity("No se encontró el odontólogo", HttpStatus.BAD_REQUEST);
        } else if (paciente == null) {
            return new ResponseEntity("No se encontró el paciente", HttpStatus.BAD_REQUEST);
        } else {
            Turno t = new Turno();
            t.setOdontologo(odontologo);
            t.setPaciente(paciente);
            t.setFechaHora(turno.getFechaHora());
            Turno turnoFinal = turnoService.createTurno(t);

            if (turnoFinal != null) {
                return new ResponseEntity(turnoService.entityToDto(turnoFinal), HttpStatus.OK);
            } else {
                return new ResponseEntity("Ocurrió un error al crear el turno", HttpStatus.BAD_GATEWAY);
            }
        }
    }

    @PutMapping("turnos/update")
    public ResponseEntity modifyTurno(@RequestBody TurnoPost turno) {
        Odontologo odontologo = odontologoService.findOdontologoById(turno.getIdOdontologo());
        Paciente paciente = pacienteService.findPacienteById(turno.getIdPaciente());
        Turno t = turnoService.findTurnoById(turno.getId(), false);

        if (t == null) {
            return new ResponseEntity("No se encontró el turno", HttpStatus.BAD_REQUEST);
        } else if (odontologo == null) {
            return new ResponseEntity("No se encontró el odontólogo", HttpStatus.BAD_REQUEST);
        } else if (paciente == null) {
            return new ResponseEntity("No se encontró el paciente", HttpStatus.BAD_REQUEST);
        } else {
            t.setOdontologo(odontologo);
            t.setPaciente(paciente);
            t.setFechaHora(turno.getFechaHora());
            Turno turnoFinal = turnoService.updateTurno(t);

            if (turnoFinal != null) {
                return new ResponseEntity(turnoService.entityToDto(turnoFinal), HttpStatus.OK);
            } else {
                return new ResponseEntity("Ocurrió un error al crear el turno", HttpStatus.BAD_GATEWAY);
            }
        }
    }

    @DeleteMapping("/turnos/{id}")
    public ResponseEntity deleteTurnoById(@PathVariable Long id) {
        Turno turno = turnoService.findTurnoById(id, false);

        if (turno == null) {
            return new ResponseEntity("No se encontró el turno", HttpStatus.BAD_REQUEST);
        } else {
            turnoService.softDeleteTurnoById(id);
            return new ResponseEntity("Turno eliminado con éxito", HttpStatus.OK);
        }
    }

    @PutMapping("turnos/restore/{id}")
    public ResponseEntity restoreTurnoById(@PathVariable Long id) {
        Turno turno = turnoService.findTurnoByIdIgnoringDeleted(id);

        if (turno == null) {
            return new ResponseEntity("No se encontró el turno", HttpStatus.BAD_REQUEST);
        } else if (turno.getDeleted() == false) {
            return new ResponseEntity("El turno de id " + id + " no fue eliminado", HttpStatus.BAD_REQUEST);
        } else {
            turnoService.restoreTurnoById(id);
            return new ResponseEntity(turnoService.entityToDto(turno), HttpStatus.OK);
        }
    }
}
