package com.facultad.repository;

import com.facultad.model.EventoFacultativo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoFacultativoRepository extends JpaRepository<EventoFacultativo, Integer> {
    List<EventoFacultativo> findByProfesorId(Integer profesorId);
    //Entidades → DTOs → Repositorios → Mappers → Servicios → Controllers ojo aca
}
