package com.facultad.service.evento;

import com.facultad.dto.EstudianteConEventosDTO;
import com.facultad.dto.EventoFacultativoDTO;
import com.facultad.mapper.EstudianteMapper;
import com.facultad.mapper.EventoFacultativoMapper;
import com.facultad.model.Estudiante;
import com.facultad.model.EventoFacultativo;
import com.facultad.model.Profesor;
import com.facultad.repository.EstudianteRepository;
import com.facultad.repository.EventoFacultativoRepository;
import com.facultad.repository.ProfesorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceEvento implements IServiceEvento {

    private final EventoFacultativoRepository eventoFacultativoRepository;
    private final ProfesorRepository profesorRepository;
    private final EstudianteRepository estudianteRepository;
    private final EstudianteMapper estudianteMapper;

    public ServiceEvento(EventoFacultativoRepository eventoFacultativoRepository,
                         ProfesorRepository profesorRepository, EstudianteRepository estudianteRepository, EstudianteMapper estudianteMapper) {
        this.eventoFacultativoRepository = eventoFacultativoRepository;
        this.profesorRepository = profesorRepository;
        this.estudianteRepository = estudianteRepository;
        this.estudianteMapper = estudianteMapper;
    }

    @Override
    public List<EventoFacultativoDTO> getAll() {
        // Recuperar todos los eventos y convertirlos a DTOs
        return eventoFacultativoRepository.findAll().stream()
                .map(EventoFacultativoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EventoFacultativoDTO save(EventoFacultativoDTO eventoFacultativoDTO) {
        // Convertir DTO a Entidad
        EventoFacultativo evento = EventoFacultativoMapper.toEntity(eventoFacultativoDTO);
        // Guardar el evento y convertirlo a DTO para el retorno
        EventoFacultativo savedEvento = eventoFacultativoRepository.save(evento);
        return EventoFacultativoMapper.toDTO(savedEvento);
    }
    @Override
    public EventoFacultativoDTO saveEventoConProfesor(Integer profesorId, EventoFacultativoDTO eventoDTO) {
        // Buscar el profesor por ID
        Profesor profesor = profesorRepository.findById(profesorId)
                .orElseThrow(() -> new IllegalArgumentException("Profesor no encontrado con ID: " + profesorId));

        // Convertir el DTO a una entidad
        EventoFacultativo evento = EventoFacultativoMapper.toEntity(eventoDTO);

        // Asociar el profesor al evento
        evento.setProfesor(profesor);

        // Guardar el evento
        EventoFacultativo savedEvento = eventoFacultativoRepository.save(evento);

        // Convertir el registro guardado a un DTO
        return EventoFacultativoMapper.toDTO(savedEvento);
    }

    @Override
    public void delete(Integer id) {
        // Verificar si existe antes de intentar eliminar
        if (!eventoFacultativoRepository.existsById(id)) {
            throw new IllegalArgumentException("El evento con ID " + id + " no existe.");
        }
        eventoFacultativoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public EventoFacultativoDTO update(EventoFacultativoDTO eventoDTO) {
        // 1. Obtener el evento existente
        EventoFacultativo eventoExistente = eventoFacultativoRepository.findById(eventoDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Evento no encontrado con ID: " + eventoDTO.getId()));

        // 2. Actualizar campos del DTO (mapeo manual para coincidir con tus nombres de campos)
        eventoExistente.setNombreEvento(eventoDTO.getNombreEvento()); // Nota: nombreEvento vs nombre

        // Manejo de fechas (asumiendo que tu entidad tiene LocalDate)
        eventoExistente.setFechaInicio(eventoDTO.getFechaInicio());
        eventoExistente.setFechaFin(eventoDTO.getFechaFin());

        // 3. Manejo seguro del profesor
        if (eventoDTO.getProfesorId() != null) {
            Profesor profesor = profesorRepository.findById(eventoDTO.getProfesorId())
                    .orElseThrow(() -> new IllegalArgumentException("Profesor no encontrado con ID: " + eventoDTO.getProfesorId()));
            eventoExistente.setProfesor(profesor);
        }
        // Si profesorId es null, mantiene el profesor actual

        // 4. Guardar cambios
        EventoFacultativo updatedEvento = eventoFacultativoRepository.save(eventoExistente);

        // 5. Convertir a DTO (ajustado a tu estructura)
        return new EventoFacultativoDTO(
                updatedEvento.getId(),
                updatedEvento.getNombreEvento(), // o getNombreEvento() según tu entidad
                updatedEvento.getFechaInicio(),
                updatedEvento.getFechaFin(),
                updatedEvento.getProfesor() != null ? updatedEvento.getProfesor().getId() : null
        );
    }

    @Override
    public EventoFacultativoDTO findOne(Integer id) {
        // Buscar la entidad y lanzar excepción si no se encuentra
        EventoFacultativo evento = eventoFacultativoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evento con ID " + id + " no encontrado."));
        // Convertir la entidad encontrada a DTO antes de devolverla
        return EventoFacultativoMapper.toDTO(evento);
    }

    @Override
    @Transactional
    public EstudianteConEventosDTO agregarEstudianteAEvento(Integer idEvento, Integer idEstudiante) {
        // 1. Buscar el evento
        EventoFacultativo evento = eventoFacultativoRepository.findById(idEvento)
                .orElseThrow(() -> new IllegalArgumentException("Evento no encontrado con ID: " + idEvento));

        // 2. Buscar el estudiante
        Estudiante estudiante = estudianteRepository.findById(idEstudiante)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado con ID: " + idEstudiante));

        // 3. Verificar si ya está asociado
        if(estudiante.getEventoFacultativo() != null &&
                estudiante.getEventoFacultativo().getId().equals(idEvento)) {
            throw new IllegalStateException("El estudiante ya está registrado en este evento");
        }

        // 4. Actualizar la relación
        estudiante.setEventoFacultativo(evento);
        Estudiante estudianteActualizado = estudianteRepository.save(estudiante);

        // 5. Retornar el DTO del estudiante con su evento
        return estudianteMapper.toConEventosDTO(estudianteActualizado);
    }
}
