package com.example.ClinicaOdontologica.repository;

import com.example.ClinicaOdontologica.model.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IOdontologoRepository extends JpaRepository<Odontologo, Long> {
    @Query(value = "SELECT * FROM odontologo WHERE id = ?1 AND deleted = false", nativeQuery = true)
    Odontologo findOdontologoById(Long id);

    @Query(value = "SELECT * FROM odontologo WHERE deleted = ?1", nativeQuery = true)
    List<Odontologo> findAllFilteringDeleted(boolean deleted);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE odontologo SET deleted=true WHERE id = ?1", nativeQuery = true)
    void softRemove(Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE odontologo SET deleted=false WHERE id = ?1", nativeQuery = true)
    void restore(Long id);
}
