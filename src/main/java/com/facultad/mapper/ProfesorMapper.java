package com.facultad.mapper;

import com.facultad.dto.ProfesorConEventosDTO;
import com.facultad.dto.ProfesorDTO;
import com.facultad.model.Profesor;

import java.util.stream.Collectors;

public class ProfesorMapper {
    public static ProfesorDTO toDTO(Profesor profesor) {
        return new ProfesorDTO(
                profesor.getId(),
                profesor.getNombre()
        );
    }

    // Convierte de DTO a Entidad
    public static Profesor toEntity(ProfesorDTO profesorDTO) {
        Profesor profesor = new Profesor();
        profesor.setId(profesorDTO.getId());
        profesor.setNombre(profesorDTO.getNombre());
        return profesor;
    }
    public static ProfesorConEventosDTO toConEventosDTO(Profesor profesor) {
        return new ProfesorConEventosDTO(
                profesor.getId(),
                profesor.getNombre(),
                profesor.getEventosFacultativos() != null
                        ? profesor.getEventosFacultativos().stream()
                        .map(EventoFacultativoMapper::toDTO)
                        .collect(Collectors.toList())
                        : null
        );
    }


}
