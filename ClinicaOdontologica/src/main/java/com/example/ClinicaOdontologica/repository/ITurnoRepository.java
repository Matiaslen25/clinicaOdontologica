package com.example.ClinicaOdontologica.repository;

import com.example.ClinicaOdontologica.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ITurnoRepository extends JpaRepository<Turno, Long> {
    @Query(value = "SELECT * FROM turno WHERE id = ?1 AND deleted = ?2", nativeQuery = true)
    Turno findTurnoById(Long id, boolean deleted);

    @Query(value = "SELECT * FROM turno WHERE odontologo_id = ?1 AND deleted = ?2 ORDER BY fecha_hora DESC", nativeQuery = true)
    List<Turno> findByOdontologoId(Long idOdontologo, boolean deleted);

    @Query(value = "SELECT * FROM turno WHERE paciente_id = ?1 AND deleted = ?2 ORDER BY fecha_hora DESC", nativeQuery = true)
    List<Turno> findByPacienteId(Long idPaciente, boolean deleted);

    @Query(value = "SELECT * FROM turno WHERE odontologo_id = ?1 AND paciente_id = ?2 AND deleted = ?3 ORDER BY fecha_hora DESC", nativeQuery = true)
    List<Turno> findByPacienteIdAndOdontologoId(Long idOdontologo, Long idPaciente, boolean deleted);

    @Query(value = "SELECT * FROM turno WHERE deleted = ?3 AND fecha_hora BETWEEN ?1 AND ?2 ORDER BY fecha_hora DESC", nativeQuery = true)
    List<Turno> findBetweenDates(LocalDate startDate, LocalDate endDate, boolean deleted);

    @Query(value = "SELECT * FROM turno WHERE deleted = ?1", nativeQuery = true)
    List<Turno> findAllFilteringDeleted(boolean deleted);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE turno SET deleted=true WHERE id=?1",nativeQuery = true)
    void softRemove(Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE turno SET deleted=false WHERE id=?1",nativeQuery = true)
    void restore(Long id);
}
