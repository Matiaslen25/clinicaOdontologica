package com.example.ClinicaOdontologica.service;

import com.example.ClinicaOdontologica.dto.PacienteDto;
import com.example.ClinicaOdontologica.model.Paciente;
import com.example.ClinicaOdontologica.repository.IPacienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    @Autowired
    private IPacienteRepository iPacienteRepository;
    private ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .findAndRegisterModules();

    public PacienteService(IPacienteRepository iPacienteRepository) {
        this.iPacienteRepository = iPacienteRepository;
    }

    public PacienteService() {
    }

    // --- CREATE --- //
    public Paciente createPaciente(Paciente paciente) {
        return this.iPacienteRepository.save(paciente);
    }
    // --- CREATE --- //


    // --- READ --- //
    public Paciente findPacienteByIdIgnoringDeleted(Long id) {
        // Devuelve un paciente independientemente de si está eliminado o no
        Optional<Paciente> paciente = this.iPacienteRepository.findById(id);

        if (paciente.isPresent()) {
            return paciente.get();
        } else {
            return null;
        }
    }

    public Paciente findPacienteById(Long id) {
        return this.iPacienteRepository.findPacienteById(id);
    }

    public List<Paciente> findAllPacientes() {
        return this.iPacienteRepository.findAll();
    }

    public List<Paciente> findAllFilteringDeleted(boolean deleted) {
        return this.iPacienteRepository.findAllFilteringDeleted(deleted);
    }
    // --- READ --- //


    // --- UPDATE --- //
    public Paciente updateById(Paciente paciente) {
        return this.iPacienteRepository.save(paciente);
    }
    // --- UPDATE --- //


    // --- DELETE --- //
    public void softDeletePacienteById(Long id) {
        this.iPacienteRepository.softRemove(id);
    }

    public void hardDeleteById(Long id) {
        this.iPacienteRepository.deleteById(id);
    }

    public void restorePacienteById(Long id) {
        this.iPacienteRepository.restore(id);
    }
    // --- DELETE --- //


    // --- DTO --- //
    public PacienteDto entityToDto(Paciente paciente) { return mapper.convertValue(paciente, PacienteDto.class); }
    public List<PacienteDto> entityListToDtoList(List<Paciente> listPacientes) {
        List<PacienteDto> dtoPacientes = new ArrayList<>();

        for (Paciente p:listPacientes) {
            dtoPacientes.add(mapper.convertValue(p, PacienteDto.class));
        }

        return dtoPacientes;
    }
    // --- DTO --- //
}
