package com.facultad.service.estudiante;

import com.facultad.dto.EstudianteConEventosDTO;
import com.facultad.dto.EstudianteCreateDTO;
import com.facultad.dto.EstudianteDTO;

import jakarta.validation.Valid;

import java.util.List;

public interface IServiceEstudiante {
    List<EstudianteDTO>getAll();
    EstudianteDTO save(@Valid EstudianteCreateDTO estudianteDTO);
    EstudianteDTO findOne(Integer id);
    EstudianteDTO update(@Valid EstudianteDTO estudianteDTO);
    void delete(Integer id);
    List<EstudianteConEventosDTO> findAllWithEvents();
    EstudianteConEventosDTO findOneWithEvents(Integer id);

    EstudianteDTO saves(@Valid EstudianteDTO estudianteDTO);
}
