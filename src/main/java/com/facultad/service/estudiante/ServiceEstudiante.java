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
        Estudiante estudiante = EstudianteMapper.toEntity(dto);

        if (dto.getEventoFacultativoId() != null) {
            EventoFacultativo evento = eventoFacultativoRepository.findById(dto.getEventoFacultativoId())
                    .orElseThrow(() -> new IllegalArgumentException("Evento no encontrado con ID: " + dto.getEventoFacultativoId()));
            estudiante.getEventosFacultativos().add(evento);
        }

        estudiante.setJustificaciones(null);
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
        if (!estudianteRepository.existsById(estudianteDTO.getId())) {
            throw new IllegalArgumentException("El estudiante con id: " + estudianteDTO.getId() + " no existe.");
        }

        Estudiante estudianteExistente = estudianteRepository.findById(estudianteDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));

        // Actualizar nombre
        estudianteExistente.setNombre(estudianteDTO.getNombre());

        // Si se pasa una lista de IDs de eventos, agregarlos
        if (estudianteDTO.getEventosFacultativosIds() != null) {
            for (Integer idEvento : estudianteDTO.getEventosFacultativosIds()) {
                EventoFacultativo evento = eventoFacultativoRepository.findById(idEvento)
                        .orElseThrow(() -> new IllegalArgumentException("Evento no encontrado con ID: " + idEvento));

                if (!estudianteExistente.getEventosFacultativos().contains(evento)) {
                    estudianteExistente.getEventosFacultativos().add(evento);
                }
            }
        }

        Estudiante actualizado = estudianteRepository.save(estudianteExistente);
        return EstudianteMapper.toDTO(actualizado);
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
