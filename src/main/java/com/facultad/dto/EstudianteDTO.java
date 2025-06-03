package com.facultad.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class EstudianteDTO {

    private Integer id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Pattern(regexp = ".*\\S.*", message = "El nombre no puede contener solo espacios")
    private String nombre;

    public EstudianteDTO(){}

    public EstudianteDTO(Integer id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }

}
