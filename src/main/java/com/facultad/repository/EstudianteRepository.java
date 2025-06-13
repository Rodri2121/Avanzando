package com.facultad.repository;

import com.facultad.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {
    List<Estudiante> findByEventoFacultativoId(Integer eventoId);
}
