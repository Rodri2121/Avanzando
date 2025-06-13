package com.facultad.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class EventoFacultativoDTO {

//aqui basicamente se declaran los atributos del evento(se tienen que poner de la misma forma que en el model
    private Integer id;

    @NotNull(message = "El nombre del evento es obligatorio")
    private String nombreEvento;

    @FutureOrPresent(message = "La fecha de inicio debe ser hoy o en el futuro")
    private LocalDate fechaInicio;

    @Future(message = "La fecha de fin debe ser en el futuro")
    private LocalDate fechaFin;

    @NotNull(message = "El ID del profesor es obligatorio")
    private Integer profesorId;

    private List<EstudianteDTO> estudiantes;



    // Constructor vacío
    public EventoFacultativoDTO() {
    }


    public EventoFacultativoDTO(Integer id, String nombreEvento, LocalDate fechaInicio, LocalDate fechaFin, Integer profesorId, List<EstudianteDTO> estudiantes) {
        this.id = id;
        this.nombreEvento = nombreEvento;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.profesorId = profesorId;
        this.estudiantes = estudiantes; // añadi una lista de estudiantes que se mapearan en el postman


    }
    public EventoFacultativoDTO(Integer id, String nombreEvento, LocalDate fechaInicio,
                                LocalDate fechaFin, Integer profesorId) {
        this(id, nombreEvento, fechaInicio, fechaFin, profesorId, null); //constructor generico inicial que permite lista null de estudiantes
    }

}
