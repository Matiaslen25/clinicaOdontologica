package com.example.ClinicaOdontologica.service;

import com.example.ClinicaOdontologica.dto.TurnoDto;
import com.example.ClinicaOdontologica.model.Turno;
import com.example.ClinicaOdontologica.repository.ITurnoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    @Autowired
    private ITurnoRepository iTurnoRepository;
    private ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .findAndRegisterModules();

    public TurnoService(ITurnoRepository iTurnoRepository) {
        this.iTurnoRepository = iTurnoRepository;
    }

    public TurnoService() {
    }

    // --- CREATE --- //
    public Turno createTurno(Turno turno) {
        return this.iTurnoRepository.save(turno);
    }
    // --- CREATE --- //


    // --- READ --- //
    public Turno findTurnoByIdIgnoringDeleted(Long id) {
        Optional<Turno> turno = this.iTurnoRepository.findById(id);

        if(turno.isPresent()) {
            return turno.get();
        } else {
            return null;
        }
    }

    public Turno findTurnoById(Long id, boolean deleted) {
        return this.iTurnoRepository.findTurnoById(id, deleted);
    }

    public List<Turno> findTurnoByPaciente(Long idPaciente, boolean deleted) {
        return this.iTurnoRepository.findByPacienteId(idPaciente, deleted);
    }

    public List<Turno> findTurnoByOdontologo(Long idOdontologo, boolean deleted) {
        return this.iTurnoRepository.findByOdontologoId(idOdontologo, deleted);
    }

    public List<Turno> findTurnosByOdontologoAndPaciente(Long idOdontologo, Long idPaciente, boolean deleted) {
        return this.iTurnoRepository.findByPacienteIdAndOdontologoId(idOdontologo, idPaciente, deleted);
    }

    public List<Turno> filterTurnosBetweenDates(LocalDate startDate, LocalDate endDate, boolean deleted) {
        return this.iTurnoRepository.findBetweenDates(startDate, endDate, deleted);
    }

    public List<Turno> findAllTurnos() {
        return this.iTurnoRepository.findAll();
    }

    public List<Turno> findAllFilteringDeleted(boolean deleted) {
        return this.iTurnoRepository.findAllFilteringDeleted(deleted);
    }
    // --- READ --- //


    // --- UPDATE --- //
    public Turno updateTurno(Turno turno) {
        return this.iTurnoRepository.save(turno);
    }
    // --- UPDATE --- //


    // --- DELETE --- //
    public void softDeleteTurnoById(Long id) {
        this.iTurnoRepository.softRemove(id);
    }

    public void hardDeleteById(Long id) {
        this.iTurnoRepository.deleteById(id);
    }

    public void restoreTurnoById(Long id) {
        this.iTurnoRepository.restore(id);
    }
    // --- DELETE --- //


    // --- DTO --- //
    public TurnoDto entityToDto(Turno turno) { return mapper.convertValue(turno, TurnoDto.class); }
    public List<TurnoDto> entityListToDtoList(List<Turno> listTurnos) {
        List<TurnoDto> dtoTurnos = new ArrayList<>();

        for (Turno t:listTurnos) {
            dtoTurnos.add(mapper.convertValue(t, TurnoDto.class));
        }

        return dtoTurnos;
    }
    // --- DTO --- //
}