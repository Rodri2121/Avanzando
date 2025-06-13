package com.facultad.controller;

import com.facultad.dto.EstudianteConEventosDTO;
import com.facultad.dto.EstudianteDTO;
import com.facultad.dto.EventoFacultativoDTO;
import com.facultad.service.evento.IServiceEvento;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    private final IServiceEvento serviceEvento;

    public EventoController(IServiceEvento serviceEvento) {
        this.serviceEvento = serviceEvento;
    }


    @GetMapping("/all")
    public List<EventoFacultativoDTO> getAllEventos() {
        return serviceEvento.getAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<EventoFacultativoDTO> getEventoById(@PathVariable Integer id) {
        EventoFacultativoDTO evento = serviceEvento.findOne(id);
        return ResponseEntity.ok(evento);
    }


    @PostMapping("/save")
    public ResponseEntity<EventoFacultativoDTO> crearEvento(@Valid @RequestBody EventoFacultativoDTO eventoDTO) {
        EventoFacultativoDTO creado = serviceEvento.save(eventoDTO);
        return ResponseEntity.ok(creado);
    }
    @PostMapping("/profesor/{profesorId}/save")
    public ResponseEntity<EventoFacultativoDTO> crearEventoConProfesor(
            @PathVariable Integer profesorId,
            @Valid @RequestBody EventoFacultativoDTO eventoDTO) {


        EventoFacultativoDTO creado = serviceEvento.saveEventoConProfesor(profesorId, eventoDTO);
        return ResponseEntity.ok(creado);
    }

    // Actualizar un evento existente
    @PutMapping("/update/{id}")
    public ResponseEntity<EventoFacultativoDTO> actualizarEvento(
            @PathVariable Integer id,
            @Valid @RequestBody EventoFacultativoDTO eventoDTO) {
        eventoDTO.setId(id);
        EventoFacultativoDTO actualizado = serviceEvento.update(eventoDTO);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar un evento por ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarEvento(@PathVariable Integer id) {
        serviceEvento.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idEvento}/estudiantes/{idEstudiante}")
    public ResponseEntity<EstudianteConEventosDTO> agregarEstudianteAEvento(
            @PathVariable Integer idEvento,
            @PathVariable Integer idEstudiante) {

        EstudianteConEventosDTO respuesta = serviceEvento.agregarEstudianteAEvento(idEvento, idEstudiante);
        return ResponseEntity.ok(respuesta);
    }
    @GetMapping("/{idEvento}/estudiantes")
    public ResponseEntity<List<EstudianteDTO>> getEstudiantesInscritos( //agregue esto que es para que aparezcan los estudiantes solo con el id del evento
            @PathVariable Integer idEvento) {

        List<EstudianteDTO> estudiantes = serviceEvento.obtenerEstudiantesInscritos(idEvento);
        return ResponseEntity.ok(estudiantes);
    }

}
