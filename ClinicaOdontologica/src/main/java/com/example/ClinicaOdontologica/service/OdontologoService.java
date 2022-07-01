package com.example.ClinicaOdontologica.service;

import com.example.ClinicaOdontologica.dto.OdontologoDto;
import com.example.ClinicaOdontologica.model.Odontologo;
import com.example.ClinicaOdontologica.repository.IOdontologoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {
    @Autowired
    private IOdontologoRepository iOdontologoRepository;
    private ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .findAndRegisterModules();

    public OdontologoService(IOdontologoRepository iOdontologoRepository) {
        this.iOdontologoRepository = iOdontologoRepository;
    }

    public OdontologoService() {
    }

    // --- CREATE --- //
    public Odontologo createOdontologo(Odontologo odontologo) {
        return this.iOdontologoRepository.save(odontologo);
    }
    // --- CREATE --- //


    // --- READ --- //
    public Odontologo findOdontologoByIdIgnoringDeleted(Long id) {
        Optional<Odontologo> odontologo = this.iOdontologoRepository.findById(id);

        if (odontologo.isPresent()) {
            return odontologo.get();
        } else {
            return null;
        }
    }

    public Odontologo findOdontologoById(Long id) {
        return this.iOdontologoRepository.findOdontologoById(id);
    }

    public List<Odontologo> findAllOdontologos() {
        return this.iOdontologoRepository.findAll();
    }

    public List<Odontologo> findAllFilteringDeleted(boolean deleted) {
        return this.iOdontologoRepository.findAllFilteringDeleted(deleted);
    }
    // --- READ --- //


    // --- UPDATE --- //
    public Odontologo updateById(Odontologo odontologo) {
        return this.iOdontologoRepository.save(odontologo);
    }
    // --- UPDATE --- //


    // --- DELETE --- //
    public void softDeleteOdontologoById(Long id) {
        this.iOdontologoRepository.softRemove(id);
    }

    public void restoreOdontologoById(Long id) {
        this.iOdontologoRepository.restore(id);
    }

    public void hardDeleteById(Long id) {
        this.iOdontologoRepository.deleteById(id);
    }
    // --- DELETE --- //


    // --- DTO --- //
    public OdontologoDto entityToDto(Odontologo odontologo) { return mapper.convertValue(odontologo, OdontologoDto.class); }
    public List<OdontologoDto> entityListToDtoList(List<Odontologo> listOdontologos) {
        List<OdontologoDto> dtoOdontologos = new ArrayList<>();

        for (Odontologo o:listOdontologos) {
            dtoOdontologos.add(mapper.convertValue(o, OdontologoDto.class));
        }

        return dtoOdontologos;
    }
    // --- DTO --- //
}
