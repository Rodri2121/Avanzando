package com.facultad.service.evento;

import com.facultad.dto.EstudianteConEventosDTO;
import com.facultad.dto.EstudianteDTO;
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

import java.util.ArrayList;
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
        Profesor profesor = profesorRepository.findById(profesorId)
                .orElseThrow(() -> new IllegalArgumentException("Profesor no encontrado"));

        EventoFacultativo evento = EventoFacultativoMapper.toEntity(eventoDTO);
        evento.setProfesor(profesor);

        //  Asociar estudiantes aquí
        if (eventoDTO.getEstudiantesIds() != null && !eventoDTO.getEstudiantesIds().isEmpty()) {
            List<Estudiante> estudiantes = estudianteRepository.findAllById(eventoDTO.getEstudiantesIds());
            evento.setEstudiantes(estudiantes);
        }

        EventoFacultativo guardado = eventoFacultativoRepository.save(evento);
        return EventoFacultativoMapper.toDTO(guardado);
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

        // 2. Actualizar campos básicos
        eventoExistente.setNombreEvento(eventoDTO.getNombreEvento());
        eventoExistente.setFechaInicio(eventoDTO.getFechaInicio());
        eventoExistente.setFechaFin(eventoDTO.getFechaFin());

        // 3. Manejar actualización del profesor si se proporciona
        if (eventoDTO.getProfesorId() != null) {
            Profesor profesor = profesorRepository.findById(eventoDTO.getProfesorId())
                    .orElseThrow(() -> new IllegalArgumentException("Profesor no encontrado con ID: " + eventoDTO.getProfesorId()));
            eventoExistente.setProfesor(profesor);
        }

        // 4. Actualizar estudiantes asignados correctamente, limpiando ambos lados de la relación
        // Limpiar la relación actual de ambos lados
        for (Estudiante estudiante : new ArrayList<>(eventoExistente.getEstudiantes())) {
            estudiante.getEventosFacultativos().remove(eventoExistente); // limpia lado inverso
        }
        eventoExistente.getEstudiantes().clear(); // limpia este lado

        // Agregar los nuevos estudiantes (si vienen)
        if (eventoDTO.getEstudiantesIds() != null && !eventoDTO.getEstudiantesIds().isEmpty()) {
            List<Estudiante> nuevosEstudiantes = estudianteRepository.findAllById(eventoDTO.getEstudiantesIds());
            for (Estudiante estudiante : nuevosEstudiantes) {
                eventoExistente.getEstudiantes().add(estudiante);
                estudiante.getEventosFacultativos().add(eventoExistente); // sincronizar lado inverso
            }
        }

        // 5. Guardar y devolver el DTO actualizado
        EventoFacultativo updatedEvento = eventoFacultativoRepository.save(eventoExistente);
        return EventoFacultativoMapper.toDTO(updatedEvento);
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

        // 3. Verificar si ya está asociado al evento
        if (estudiante.getEventosFacultativos().contains(evento)) {
            throw new IllegalStateException("El estudiante ya está registrado en este evento");
        }

        // 4. Asociar el evento al estudiante
        estudiante.getEventosFacultativos().add(evento);
        estudianteRepository.save(estudiante);

        // 5. Retornar el DTO del estudiante con su lista de eventos
        return estudianteMapper.toConEventosDTO(estudiante);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstudianteDTO> obtenerEstudiantesInscritos(Integer idEvento) {
        // Verificar que el evento existe
        if (!eventoFacultativoRepository.existsById(idEvento)) {
            throw new IllegalStateException("Evento no encontrado con ID: " + idEvento);
        }

        // Obtener estudiantes usando el método del repositorio
        List<Estudiante> estudiantes = estudianteRepository.findByEventoFacultativoId(idEvento);

        // Convertir a DTOs usando el mapper ya inyectado
        return estudiantes.stream()
                .map(EstudianteMapper::toDTO)
                .collect(Collectors.toList());
    }
}
