package com.facultad.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EstudianteCreateDTO {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre; //para crear un estudiante se pone un nombre

   // @NotNull(message = "Debe asignar un evento facultativo")
    private Integer eventoFacultativoId;//se le debe asignar un id al estudiante(todo esto mediante postman)

    private Integer justificacionId; // Opcional
}