package com.facultad.service.evento;

import com.facultad.dto.EstudianteConEventosDTO;
import com.facultad.dto.EventoFacultativoDTO;

import java.util.List;

public interface IServiceEvento {
    List<EventoFacultativoDTO> getAll();
    EventoFacultativoDTO save(EventoFacultativoDTO eventoFacultativoDTO);
    void delete(Integer id);
    EventoFacultativoDTO update(EventoFacultativoDTO eventoFacultativoDTO);
    EventoFacultativoDTO findOne(Integer id);
    EventoFacultativoDTO saveEventoConProfesor(Integer profesorId, EventoFacultativoDTO eventoDTO);
    EstudianteConEventosDTO agregarEstudianteAEvento(Integer idEvento, Integer idEstudiante);
}
