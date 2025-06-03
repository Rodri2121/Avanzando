package com.facultad.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class EventoFacultativoDTO {

    // Getters y Setters
    private Integer id;

    @NotNull(message = "El nombre del evento es obligatorio")
    private String nombreEvento;

    @FutureOrPresent(message = "La fecha de inicio debe ser hoy o en el futuro")
    private LocalDate fechaInicio;

    @Future(message = "La fecha de fin debe ser en el futuro")
    private LocalDate fechaFin;

    @NotNull(message = "El ID del profesor es obligatorio")
    private Integer profesorId;



    // Constructor vacío
    public EventoFacultativoDTO() {
    }


    public EventoFacultativoDTO(Integer id, String nombreEvento, LocalDate fechaInicio, LocalDate fechaFin, Integer profesorId) {
        this.id = id;
        this.nombreEvento = nombreEvento;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.profesorId = profesorId;

    }

}
