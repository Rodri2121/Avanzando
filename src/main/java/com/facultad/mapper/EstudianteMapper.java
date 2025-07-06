package com.facultad.mapper;

import com.facultad.dto.EstudianteConEventosDTO;
import com.facultad.dto.EstudianteCreateDTO;
import com.facultad.dto.EstudianteDTO;
import com.facultad.dto.EventoFacultativoDTO;
import com.facultad.model.Estudiante;
import com.facultad.model.EventoFacultativo;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstudianteMapper {

    public static EstudianteDTO toDTO(Estudiante estudiante) {
        EstudianteDTO dto = new EstudianteDTO();
        dto.setId(estudiante.getId());
        dto.setNombre(estudiante.getNombre());

        // Mapear todos los IDs de los eventos asignados
        if (estudiante.getEventosFacultativos() != null && !estudiante.getEventosFacultativos().isEmpty()) {
            List<Integer> ids = estudiante.getEventosFacultativos()
                    .stream()
                    .map(EventoFacultativo::getId)
                    .collect(Collectors.toList());

            dto.setEventosFacultativosIds(ids);
        }

        return dto;
    }

    public static Estudiante toEntity(@Valid EstudianteCreateDTO dto) {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(dto.getNombre());
        // La asociación con eventos se hace en el servicio, no aquí
        return estudiante;
    }

    public EstudianteConEventosDTO toConEventosDTO(Estudiante estudiante) {
        List<EventoFacultativoDTO> eventosDTO = null;

        if (estudiante.getEventosFacultativos() != null && !estudiante.getEventosFacultativos().isEmpty()) {
            eventosDTO = estudiante.getEventosFacultativos()
                    .stream()
                    .map(EventoFacultativoMapper::toDTO)
                    .collect(Collectors.toList());
        }

        return new EstudianteConEventosDTO(
                estudiante.getId(),
                estudiante.getNombre(),
                eventosDTO
        );
    }
}

