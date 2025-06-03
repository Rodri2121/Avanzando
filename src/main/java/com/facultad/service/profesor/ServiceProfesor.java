package com.facultad.service.profesor;

import com.facultad.dto.ProfesorConEventosDTO;
import com.facultad.dto.ProfesorDTO;
import com.facultad.mapper.ProfesorMapper;
import com.facultad.model.Profesor;
import com.facultad.repository.ProfesorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceProfesor implements IServiceProfesor {
    private final ProfesorRepository profesorRepository;

    public ServiceProfesor(ProfesorRepository profesorRepository) {
        this.profesorRepository = profesorRepository;
    }

    @Override
    public List<ProfesorDTO> getAll() {
        return profesorRepository.findAll().stream()
                .map(ProfesorMapper::toDTO)
                .toList();
    }

    @Override
    public ProfesorDTO findOne(Integer id) {
        Profesor profesor = profesorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El profesor con ID " + id + " no existe."));
        return ProfesorMapper.toDTO(profesor);
    }

    @Override
    public ProfesorDTO save(ProfesorDTO profesorDTO) {
        Profesor profesor = ProfesorMapper.toEntity(profesorDTO);
        Profesor savedProfesor = profesorRepository.save(profesor);
        return ProfesorMapper.toDTO(savedProfesor);
    }

    @Override
    public ProfesorDTO update(ProfesorDTO profesorDTO) {
        if (!profesorRepository.existsById(profesorDTO.getId())) {
            throw new IllegalArgumentException("El profesor con ID " + profesorDTO.getId() + " no existe.");
        }
        Profesor profesor = ProfesorMapper.toEntity(profesorDTO);
        Profesor updatedProfesor = profesorRepository.save(profesor);
        return ProfesorMapper.toDTO(updatedProfesor);
    }

    @Override
    public void delete(Integer id) {
        if (!profesorRepository.existsById(id)) {
            throw new IllegalArgumentException("El profesor con ID " + id + " no existe.");
        }
        profesorRepository.deleteById(id);
    }
    @Override
    public List<ProfesorConEventosDTO> findAllWithEvents() {
        return profesorRepository.findAll().stream()
                .map(ProfesorMapper::toConEventosDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProfesorConEventosDTO findOneWithEvents(Integer id) {
        Profesor profesor = profesorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Profesor no encontrado con ID: " + id));
        return ProfesorMapper.toConEventosDTO(profesor);
    }
}
