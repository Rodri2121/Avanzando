package com.facultad.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EstudianteCreateDTO {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotNull(message = "Debe asignar un evento facultativo")
    private Integer eventoFacultativoId;

    private Integer justificacionId; // Opcional
}