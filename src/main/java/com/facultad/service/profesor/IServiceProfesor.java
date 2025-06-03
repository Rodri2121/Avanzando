package com.facultad.service.profesor;

import com.facultad.dto.ProfesorConEventosDTO;
import com.facultad.dto.ProfesorDTO;

import java.util.List;

public interface IServiceProfesor {
    List<ProfesorDTO> getAll();
    ProfesorDTO findOne(Integer id);
    ProfesorDTO save(ProfesorDTO profesorDTO);
    ProfesorDTO update(ProfesorDTO profesorDTO);
    void delete(Integer id);
    List<ProfesorConEventosDTO> findAllWithEvents(); // Todos los profesores con eventos
    ProfesorConEventosDTO findOneWithEvents(Integer id);
}
