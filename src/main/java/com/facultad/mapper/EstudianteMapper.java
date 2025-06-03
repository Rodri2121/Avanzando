package com.facultad.mapper;

import com.facultad.dto.EstudianteConEventosDTO;
import com.facultad.dto.EstudianteCreateDTO;
import com.facultad.dto.EstudianteDTO;
import com.facultad.model.Estudiante;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class EstudianteMapper {
    public static EstudianteDTO toDTO(Estudiante estudiante) {
        EstudianteDTO dto = new EstudianteDTO();
        dto.setId(estudiante.getId());
        dto.setNombre(estudiante.getNombre());
        if(estudiante.getEventoFacultativo() != null) {
            dto.setId(estudiante.getEventoFacultativo().getId());
        }

        return dto;
    }

    public static Estudiante toEntity(@Valid EstudianteCreateDTO dto) {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(dto.getNombre());
        // No mapear justificación
        return estudiante;
    }

    public EstudianteConEventosDTO toConEventosDTO(Estudiante estudiante) {
        return new EstudianteConEventosDTO(
                estudiante.getId(),
                estudiante.getNombre(),
                estudiante.getEventoFacultativo() != null
                        ? Collections.singletonList(EventoFacultativoMapper.toDTO(estudiante.getEventoFacultativo()))
                        : null
        );//solucion para un caso many to one y que solo me pase el evento al que esta asignado
    }


}
