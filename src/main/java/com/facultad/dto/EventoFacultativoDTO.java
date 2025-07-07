package com.facultad.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class EventoFacultativoDTO {

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


    private List<Integer> estudiantesIds;

    public EventoFacultativoDTO() {}

    public EventoFacultativoDTO(
            Integer id,
            String nombreEvento,
            LocalDate fechaInicio,
            LocalDate fechaFin,
            Integer profesorId,
            List<EstudianteDTO> estudiantes,
            List<Integer> estudiantesIds
    ) {
        this.id = id;
        this.nombreEvento = nombreEvento;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.profesorId = profesorId;
        this.estudiantes = estudiantes;
        this.estudiantesIds = estudiantesIds;
    }

    public EventoFacultativoDTO(
            Integer id,
            String nombreEvento,
            LocalDate fechaInicio,
            LocalDate fechaFin,
            Integer profesorId
    ) {
        this(id, nombreEvento, fechaInicio, fechaFin, profesorId, null, null);
    }
}

