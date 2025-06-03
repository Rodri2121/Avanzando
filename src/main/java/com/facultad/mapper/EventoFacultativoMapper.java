package com.facultad.mapper;

import com.facultad.dto.EventoFacultativoDTO;
import com.facultad.model.EventoFacultativo;

public class EventoFacultativoMapper {

    public static EventoFacultativoDTO toDTO(EventoFacultativo evento) {
        return new EventoFacultativoDTO(
                evento.getId(),
                evento.getNombreEvento(),
                evento.getFechaInicio(),
                evento.getFechaFin(),
                evento.getProfesor().getId()
        );
    }

    public static EventoFacultativo toEntity(EventoFacultativoDTO dto) {
        EventoFacultativo evento = new EventoFacultativo();
        evento.setId(dto.getId());
        evento.setNombreEvento(dto.getNombreEvento());
        evento.setFechaInicio(dto.getFechaInicio());
        evento.setFechaFin(dto.getFechaFin());

        return evento;
    }
}
