package com.example.ClinicaOdontologica.controller;

import com.example.ClinicaOdontologica.dto.TurnoPost;
import com.example.ClinicaOdontologica.dto.TurnoWithDatesDto;
import com.example.ClinicaOdontologica.model.Odontologo;
import com.example.ClinicaOdontologica.model.Paciente;
import com.example.ClinicaOdontologica.model.Turno;
import com.example.ClinicaOdontologica.service.OdontologoService;
import com.example.ClinicaOdontologica.service.PacienteService;
import com.example.ClinicaOdontologica.service.TurnoService;
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
public class TurnoController {
    private static final Logger logger = Logger.getLogger(OdontologoController.class);

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
                logger.info("Buscando los turnos activos que comparten el odontólogo con id " + idOdontologo + " y el paciente con id " + idPaciente);
                turnos = turnoService.findTurnosByOdontologoAndPaciente(idOdontologo, idPaciente, false);
            } else {
                // El usuario está buscando turnos solo por odontólogo
                logger.info("Buscando los turnos activos del odontólogo con id " + idOdontologo);
                turnos = turnoService.findTurnoByOdontologo(idOdontologo, false);
            }
        } else if (idPaciente != null) {
            // El usuario está buscando turnos solo por paciente
            logger.info("Buscando los turnos activos del paciente con id " + idPaciente);
            turnos = turnoService.findTurnoByPaciente(idPaciente, false);
        } else {
            // El usuario está buscando todos los turnos
            logger.info("Buscando todos los turnos activos");
            turnos = turnoService.findAllFilteringDeleted(false);
        }

        if(turnos == null || turnos.size() < 1) {
            logger.error("No se encontraron los turnos solicitados");
            return new ResponseEntity("No se encontraron turnos", HttpStatus.NOT_FOUND);
        } else {
            logger.info(turnos.size() + " turnos encontrados con éxito");
            return new ResponseEntity(turnoService.entityListToDtoList(turnos), HttpStatus.OK);
        }
    }

    @GetMapping("/turnos/deleted")
    public ResponseEntity getAllDeleted(@RequestParam(name="paciente", required = false) Long idPaciente, @RequestParam(name = "odontologo", required = false) Long idOdontologo) {
        List<Turno> turnos;

        if (idOdontologo != null) {
            if (idPaciente != null) {
                // El usuario está buscando turnos por odontólogo y por paciente
                logger.info("Buscando los turnos eliminados que comparten el odontólogo con id " + idOdontologo + " y el paciente con id " + idPaciente);
                turnos = turnoService.findTurnosByOdontologoAndPaciente(idOdontologo, idPaciente, true);
            } else {
                // El usuario está buscando turnos solo por odontólogo
                logger.info("Buscando los turnos eliminados del odontólogo con id " + idOdontologo);
                turnos = turnoService.findTurnoByOdontologo(idOdontologo, true);
            }
        } else if (idPaciente != null) {
            // El usuario está buscando turnos solo por paciente
            logger.info("Buscando los turnos eliminados del paciente con id " + idPaciente);
            turnos = turnoService.findTurnoByPaciente(idPaciente, true);
        } else {
            // El usuario está buscando todos los turnos
            logger.info("Buscando todos los turnos eliminados");
            turnos = turnoService.findAllFilteringDeleted(true);
        }

        if(turnos == null || turnos.size() < 1) {
            logger.error("No se encontraron los turnos solicitados");
            return new ResponseEntity("No se encontraron turnos", HttpStatus.NOT_FOUND);
        } else {
            logger.info(turnos.size() + " turnos encontrados con éxito");
            return new ResponseEntity(turnoService.entityListToDtoList(turnos), HttpStatus.OK);
        }
    }

    @GetMapping("/turnos/{id}")
    public ResponseEntity getTurnoById(@PathVariable Long id) {
        logger.info("Buscando turno activo con id " + id);
        Turno turno = turnoService.findTurnoById(id, false);

        if (turno == null) {
            logger.error("Turno activo con id " + id + " no encontrado");
            return new ResponseEntity("No se encontró el turno", HttpStatus.BAD_REQUEST);
        } else {
            logger.info("Turno del paciente " + turno.getPaciente().getNombre() + " "  + turno.getPaciente().getApellido() + " y del odontólogo " + turno.getOdontologo().getNombre() + " "  + turno.getOdontologo().getApellido() + " encontrado");
            return new ResponseEntity(turnoService.entityToDto(turno), HttpStatus.OK);
        }
    }

    @GetMapping("/turnos/deleted/{id}")
    public ResponseEntity getDeletedTurnoById(@PathVariable Long id) {
        logger.info("Buscando turno eliminado con id " + id);
        Turno turno = turnoService.findTurnoById(id, true);

        if (turno == null) {
            logger.error("Turno eliminado con id " + id + " no encontrado");
            return new ResponseEntity("No se encontró el turno", HttpStatus.BAD_REQUEST);
        } else {
            logger.info("Turno eliminado del paciente " + turno.getPaciente().getNombre() + " "  + turno.getPaciente().getApellido() + " y del odontólogo " + turno.getOdontologo().getNombre() + " "  + turno.getOdontologo().getApellido() + " encontrado");
            return new ResponseEntity(turnoService.entityToDto(turno), HttpStatus.OK);
        }
    }

    @PostMapping("/turnos/fechas")
    public ResponseEntity findTurnosBetweenDates(@RequestBody TurnoWithDatesDto dates) {
        logger.info("Buscando turnos entre 2 fechas");
        if (dates.getFechaInicio() == null || dates.getFechaFin() == null) {
            logger.error("No se indicaron las fechas para buscar los turnos");
            return new ResponseEntity("Indique fecha de incio y de fin", HttpStatus.BAD_REQUEST);
        } else {
            List<Turno> turnos = turnoService.filterTurnosBetweenDates(dates.getFechaInicio(), dates.getFechaFin(), false);

            if (turnos == null || turnos.size() < 1) {
                logger.error("No se encontraron turnos entre " + dates.getFechaInicio() + " y " + dates.getFechaFin());
                return new ResponseEntity("No se encontraron turnos", HttpStatus.BAD_REQUEST);
            } else {
                logger.info(turnos.size() + " turnos entre " + dates.getFechaInicio() + " y " + dates.getFechaFin() + " encontrados con éxito");
                return new ResponseEntity(turnoService.entityListToDtoList(turnos), HttpStatus.OK);
            }
        }
    }

    @PostMapping("/turnos/deleted/fechas")
    public ResponseEntity findDeletedTurnosBetweenDates(@RequestBody  TurnoWithDatesDto dates) {
        logger.info("Buscando turnos eliminados entre 2 fechas");
        if (dates.getFechaInicio() == null || dates.getFechaFin() == null) {
            logger.error("No se indicaron las fechas para buscar los turnos eliminados");
            return new ResponseEntity("Indique fecha de incio y de fin", HttpStatus.BAD_REQUEST);
        } else {
            List<Turno> turnos = turnoService.filterTurnosBetweenDates(dates.getFechaInicio(), dates.getFechaFin(), true);

            if (turnos == null || turnos.size() < 1) {
                logger.error("No se encontraron turnos eliminados entre " + dates.getFechaInicio() + " y " + dates.getFechaFin());
                return new ResponseEntity("No se encontraron turnos", HttpStatus.BAD_REQUEST);
            } else {
                logger.info(turnos.size() + " turnos eliminados entre " + dates.getFechaInicio() + " y " + dates.getFechaFin() + " encontrados con éxito");
                return new ResponseEntity(turnoService.entityListToDtoList(turnos), HttpStatus.OK);
            }
        }
    }

    @PostMapping("/turnos")
    public ResponseEntity saveTurno(@RequestBody TurnoPost turno) {
        logger.info("Creando turno");
        Odontologo odontologo = odontologoService.findOdontologoById(turno.getIdOdontologo());
        Paciente paciente = pacienteService.findPacienteById(turno.getIdPaciente());
        if (odontologo == null) {
            logger.error("No se indicó odontólogo para crear el turno");
            return new ResponseEntity("No se encontró el odontólogo", HttpStatus.BAD_REQUEST);
        } else if (paciente == null) {
            logger.error("No se indicó paciente para crear el turno");
            return new ResponseEntity("No se encontró el paciente", HttpStatus.BAD_REQUEST);
        } else {
            Turno t = new Turno();
            t.setOdontologo(odontologo);
            t.setPaciente(paciente);
            t.setFechaHora(turno.getFechaHora());
            Turno turnoFinal = turnoService.createTurno(t);

            if (turnoFinal != null) {
                logger.info("Turno creado con éxito");
                return new ResponseEntity(turnoService.entityToDto(turnoFinal), HttpStatus.OK);
            } else {
                logger.error("Ocurrió un error al crear el turno");
                return new ResponseEntity("Ocurrió un error al crear el turno", HttpStatus.BAD_GATEWAY);
            }
        }
    }

    @PutMapping("turnos/update")
    public ResponseEntity modifyTurno(@RequestBody TurnoPost turno) {
        logger.info("Editando turno con id " + turno.getId());
        Odontologo odontologo = odontologoService.findOdontologoById(turno.getIdOdontologo());
        Paciente paciente = pacienteService.findPacienteById(turno.getIdPaciente());
        Turno t = turnoService.findTurnoById(turno.getId(), false);

        if (t == null) {
            logger.error("Ocurrió un error al editar el turno. No fue encontrado el turno con id " + turno.getId());
            return new ResponseEntity("No se encontró el turno", HttpStatus.BAD_REQUEST);
        } else if (odontologo == null) {
            logger.error("Ocurrió un error al editar el turno. No fue encontrado el odontólogo con id " + turno.getIdOdontologo());
            return new ResponseEntity("No se encontró el odontólogo", HttpStatus.BAD_REQUEST);
        } else if (paciente == null) {
            logger.error("Ocurrió un error al editar el turno. No fue encontrado el paciente con id " + turno.getIdPaciente());
            return new ResponseEntity("No se encontró el paciente", HttpStatus.BAD_REQUEST);
        } else {
            t.setOdontologo(odontologo);
            t.setPaciente(paciente);
            t.setFechaHora(turno.getFechaHora());
            Turno turnoFinal = turnoService.updateTurno(t);

            if (turnoFinal != null) {
                logger.info("Turno modificado con éxito");
                return new ResponseEntity(turnoService.entityToDto(turnoFinal), HttpStatus.OK);
            } else {
                logger.error("Ocurrió un error al modificar el turno");
                return new ResponseEntity("Ocurrió un error al crear el turno", HttpStatus.BAD_GATEWAY);
            }
        }
    }

    @DeleteMapping("/turnos/{id}")
    public ResponseEntity deleteTurnoById(@PathVariable Long id) {
        logger.info("Eliminando turno con id " + id);
        Turno turno = turnoService.findTurnoById(id, false);

        if (turno == null) {
            logger.error("No se encontró el turno con id " + id + " para eliminarlo");
            return new ResponseEntity("No se encontró el turno", HttpStatus.BAD_REQUEST);
        } else {
            turnoService.softDeleteTurnoById(id);
            logger.info("Turno con id " + id + " eliminado con éxito");
            return new ResponseEntity("Turno eliminado con éxito", HttpStatus.OK);
        }
    }

    @PutMapping("turnos/restore/{id}")
    public ResponseEntity restoreTurnoById(@PathVariable Long id) {
        logger.info("Restaurando turno con id " + id);
        Turno turno = turnoService.findTurnoByIdIgnoringDeleted(id);

        if (turno == null) {
            logger.error("Ocurrió un error al restaurar el turno con id " + id + ". No fue encontrado");
            return new ResponseEntity("No se encontró el turno", HttpStatus.BAD_REQUEST);
        } else if (turno.getDeleted() == false) {
            logger.error("Ocurrió un error al restaurar el turno con id " + id + ". El turno no se encuentra eliminado");
            return new ResponseEntity("El turno de id " + id + " no fue eliminado", HttpStatus.BAD_REQUEST);
        } else {
            turnoService.restoreTurnoById(id);
            logger.info("Turno con id " + id + " restaurado con éxito");
            return new ResponseEntity(turnoService.entityToDto(turno), HttpStatus.OK);
        }
    }
}
