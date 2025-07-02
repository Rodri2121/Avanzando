package com.facultad.controller;


import com.facultad.dto.EstudianteConEventosDTO;
import com.facultad.dto.EstudianteCreateDTO;
import com.facultad.dto.EstudianteDTO;
import com.facultad.service.estudiante.IServiceEstudiante;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {

    /*
    Hago uso de los metodos del serviceEstudiante
     */


    private final IServiceEstudiante serviceEstudiante;

    public EstudianteController(IServiceEstudiante serviceEstudiante) {
        this.serviceEstudiante = serviceEstudiante;
    }
    @GetMapping("/all") //se manda a llamar el metodo del servicio para que se muestren todos los estudiantes agregados
    public List<EstudianteDTO>getAll(){
        return serviceEstudiante.getAll();
    }

    @GetMapping("/allWithEvents") //en este caso se muestran todos los estudiantes pero tambien se muestran los eventos a los que estan inscritos
    public List<EstudianteConEventosDTO>getAllEstudiantesConEvento(){
        return serviceEstudiante.findAllWithEvents();
    }

    @GetMapping("/OneWithEvent/{id}") //aqui solo se llama a un estudiante mediante el id y muestra el evento al que esta incrito
    public EstudianteConEventosDTO EncontrarConEvento(@PathVariable Integer id){
        return serviceEstudiante.findOneWithEvents(id);
    }

    @GetMapping("/{id}")//muestra el estudiante sin mas
    public EstudianteDTO getEstudianteById(@PathVariable Integer id){
        return serviceEstudiante.findOne(id);
    }

    @PostMapping("/save")//usa el metodo "save" para guardar al estudiante. Tambien se hace uso de anotaciones de spring para recibir los datos
    public ResponseEntity<?> crearEstudiante(@RequestBody @Valid EstudianteCreateDTO estudianteDTO, //y se validan datos con el @Valid
                                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) { //manejo errores en caso de que se digite algo mal en la prueba
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores); //y devuelve mensaje personalizado
        }

        EstudianteDTO creado = serviceEstudiante.save(estudianteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado); //manda una confirmacion en caso de que se cree correctamente
    }

//    @PostMapping("/saveEs")
//    public ResponseEntity<EstudianteDTO> guardarEstudiante(@Valid @RequestBody EstudianteDTO estudianteDTO){
//        EstudianteDTO guardado = serviceEstudiante.saves(estudianteDTO);
//        return ResponseEntity.ok(guardado);
//    }




    @PutMapping("/update/{id}")
    public ResponseEntity<EstudianteDTO>actualizarEstudiante(
            @PathVariable Integer id,
            @Valid @RequestBody EstudianteDTO estudianteDTO){ //igual que el save pero en este caso hay que pasarle el id pasa especificar a quien se quiere editar
        estudianteDTO.setId(id);
        EstudianteDTO actualizado = serviceEstudiante.update(estudianteDTO);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/delete/{id}") //se le pasa un id conocido que se desee eliminar
    public ResponseEntity<Void>eliminarEstudiante(@PathVariable Integer id){
        serviceEstudiante.delete(id);
        return ResponseEntity.noContent().build();
    }
}
