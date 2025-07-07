package com.facultad.mapper;

import com.facultad.dto.EstudianteDTO;
import com.facultad.dto.EventoFacultativoDTO;
import com.facultad.model.Estudiante;
import com.facultad.model.EventoFacultativo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


public class EventoFacultativoMapper {

    public static EventoFacultativoDTO toDTO(EventoFacultativo evento) {
        List<EstudianteDTO> estudiantesDTO = null;
        List<Integer> estudiantesIds = null;

        if (evento.getEstudiantes() != null && !evento.getEstudiantes().isEmpty()) {
            estudiantesDTO = evento.getEstudiantes().stream()
                    .map(EstudianteMapper::toDTO)
                    .collect(Collectors.toList());

            estudiantesIds = evento.getEstudiantes().stream()
                    .map(Estudiante::getId)
                    .collect(Collectors.toList());
        }

        EventoFacultativoDTO dto = new EventoFacultativoDTO(
                evento.getId(),
                evento.getNombreEvento(),
                evento.getFechaInicio(),
                evento.getFechaFin(),
                evento.getProfesor().getId(),
                estudiantesDTO,
                estudiantesIds
        );

        return dto;
    }

    public static EventoFacultativo toEntity(EventoFacultativoDTO dto) {
        EventoFacultativo evento = new EventoFacultativo();
        evento.setId(dto.getId());
        evento.setNombreEvento(dto.getNombreEvento());
        evento.setFechaInicio(dto.getFechaInicio());
        evento.setFechaFin(dto.getFechaFin());
        // ⚠️ Estudiantes se asignan aparte en el servicio
        return evento;
    }
}