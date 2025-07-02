package com.facultad.controller;

import com.facultad.dto.ProfesorConEventosDTO;
import com.facultad.dto.ProfesorDTO;
import com.facultad.service.profesor.IServiceProfesor;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profesores")
public class ProfesorController {

    private final IServiceProfesor serviceProfesor;

    public ProfesorController(IServiceProfesor serviceProfesor) {
        this.serviceProfesor = serviceProfesor;
    }


    @GetMapping("/all")
    public List<ProfesorConEventosDTO> getAllProfesoresConEventos() {
        return serviceProfesor.findAllWithEvents();
    }

    @GetMapping("/profe/{id}")
    public ProfesorConEventosDTO EncontrarEvento(@PathVariable Integer id){
        return serviceProfesor.findOneWithEvents(id);
    }



    @GetMapping("/{id}")
    public ProfesorDTO getProfesorById(@PathVariable Integer id) {
        return serviceProfesor.findOne(id);
    }


    @PostMapping("/save")
    public ResponseEntity<ProfesorDTO> crearProfesor(@Valid @RequestBody ProfesorDTO profesorDTO) {
        ProfesorDTO creado = serviceProfesor.save(profesorDTO);
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProfesorDTO> actualizarProfesor(
            @PathVariable Integer id,
            @Valid @RequestBody ProfesorDTO profesorDTO) {
        profesorDTO.setId(id);
        ProfesorDTO actualizado = serviceProfesor.update(profesorDTO);
        return ResponseEntity.ok(actualizado);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarProfesor(@PathVariable Integer id) {
        serviceProfesor.delete(id);
        return ResponseEntity.noContent().build();
    }
}
