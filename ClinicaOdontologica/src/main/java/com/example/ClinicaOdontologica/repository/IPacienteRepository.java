package com.example.ClinicaOdontologica.repository;

import com.example.ClinicaOdontologica.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IPacienteRepository extends JpaRepository<Paciente, Long> {
    @Query(value = "SELECT * FROM paciente WHERE id = ?1 AND deleted = false", nativeQuery = true)
    Paciente findPacienteById(Long id);

    @Query(value = "SELECT * FROM paciente WHERE deleted = ?1", nativeQuery = true)
    List<Paciente> findAllFilteringDeleted(boolean deleted);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE paciente SET deleted=true WHERE id=?1", nativeQuery = true)
    void softRemove(Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE paciente SET deleted=false WHERE id=?1", nativeQuery = true)
    void restore(Long id);
}
