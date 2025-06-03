package com.facultad.service.estudiante;

import com.facultad.dto.EstudianteConEventosDTO;
import com.facultad.dto.EstudianteCreateDTO;
import com.facultad.dto.EstudianteDTO;
import com.facultad.mapper.EstudianteMapper;
import com.facultad.model.Estudiante;
import com.facultad.model.EventoFacultativo;
import com.facultad.repository.EstudianteRepository;
import com.facultad.repository.EventoFacultativoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceEstudiante implements IServiceEstudiante{

    private final EstudianteRepository estudianteRepository;
    private final EstudianteMapper estudianteMapper;
    private final EventoFacultativoRepository eventoFacultativoRepository;

    public ServiceEstudiante(EstudianteRepository estudianteRepository, EstudianteMapper estudianteMapper, EventoFacultativoRepository eventoFacultativoRepository){
        this.estudianteRepository = estudianteRepository;
        this.estudianteMapper = estudianteMapper;
        this.eventoFacultativoRepository = eventoFacultativoRepository;
    }
    @Override
    public List<EstudianteDTO> getAll() {
        return estudianteRepository.findAll()
                .stream()
                .map(EstudianteMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public EstudianteDTO save(@Valid EstudianteCreateDTO dto) {
        // Validar y obtener el evento
        EventoFacultativo evento = eventoFacultativoRepository.findById(dto.getEventoFacultativoId())
                .orElseThrow(() -> new IllegalArgumentException("Evento no encontrado con ID: " + dto.getEventoFacultativoId()));

        // Crear entidad
        Estudiante estudiante = EstudianteMapper.toEntity(dto);
        estudiante.setEventoFacultativo(evento);
        // No establecer justificación aunque venga en el DTO
        estudiante.setJustificaciones(null);

        // Guardar y retornar DTO
        Estudiante saved = estudianteRepository.save(estudiante);
        return EstudianteMapper.toDTO(saved);
    }

    @Override
    public EstudianteDTO findOne(Integer id) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("El estudiante con id: " + id + " no fue encontrado."));
        return EstudianteMapper.toDTO(estudiante);
    }

    @Override
    public EstudianteDTO update(@Valid EstudianteDTO estudianteDTO) {
        if(!estudianteRepository.existsById(estudianteDTO.getId())){
            throw new IllegalArgumentException("El estudiante con id: " + estudianteDTO.getId() + " no existe.");
        }

        // Obtener el estudiante existente
        Estudiante estudianteExistente = estudianteRepository.findById(estudianteDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));

        // Actualizar solo los campos permitidos (nombre en este caso)
        estudianteExistente.setNombre(estudianteDTO.getNombre());

        // Guardar los cambios
        Estudiante updatedEstudiante = estudianteRepository.save(estudianteExistente);
        return EstudianteMapper.toDTO(updatedEstudiante);
    }

    @Override
    public void delete(Integer id) {
        if(!estudianteRepository.existsById(id)){
            throw new IllegalArgumentException("El estudiante con id: " + id + " no fue encontrado.");
        }
        estudianteRepository.deleteById(id);

    }

    @Override
    public List<EstudianteConEventosDTO> findAllWithEvents() {
        return estudianteRepository.findAll().stream()
                .map(estudianteMapper::toConEventosDTO) // Usar mapper inyectado
                .collect(Collectors.toList());
    }

    @Override
    public EstudianteConEventosDTO findOneWithEvents(Integer id) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no fue encontrado con ID: " + id));
        return estudianteMapper.toConEventosDTO(estudiante); // Usar mapper inyectado
    }

    @Override
    public EstudianteDTO saves(EstudianteDTO estudianteDTO) {
        return null;
    }
}
