package com.facultad.repository;

import com.facultad.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {
    @Query("SELECT e FROM Estudiante e JOIN e.eventosFacultativos ef WHERE ef.id = :eventoId")
    List<Estudiante> findByEventoFacultativoId(@Param("eventoId") Integer eventoId);
}
