package com.facultad.mapper;

import com.facultad.dto.EstudianteDTO;
import com.facultad.dto.EventoFacultativoDTO;
import com.facultad.model.EventoFacultativo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


public class EventoFacultativoMapper {

    // No necesitamos inyectar EstudianteMapper porque tus métodos son estáticos

    public static EventoFacultativoDTO toDTO(EventoFacultativo evento) {
        List<EstudianteDTO> estudiantesDTO = null;

        if(evento.getEstudiantes() != null && !evento.getEstudiantes().isEmpty()) {
            estudiantesDTO = evento.getEstudiantes().stream()
                    .map(EstudianteMapper::toDTO) // EstudianteMapper existente y tambien esto cumple con que aparezcan los estudiantes en el evento
                    .collect(Collectors.toList());
        }

        return new EventoFacultativoDTO(
                evento.getId(),
                evento.getNombreEvento(),
                evento.getFechaInicio(),
                evento.getFechaFin(),
                evento.getProfesor().getId(),
                estudiantesDTO // Lista de estudiantes convertidos, agregada tambien
        );
    }

    public static EventoFacultativo toEntity(EventoFacultativoDTO dto) {
        EventoFacultativo evento = new EventoFacultativo();
        evento.setId(dto.getId());
        evento.setNombreEvento(dto.getNombreEvento());
        evento.setFechaInicio(dto.getFechaInicio());
        evento.setFechaFin(dto.getFechaFin());
        // No asignamos estudiantes aquí (se maneja en el servicio)
        return evento;
    }
}
