package com.facultad.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Data
public class EstudianteDTO {

    private Integer id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Pattern(regexp = ".*\\S.*", message = "El nombre no puede contener solo espacios") //esto lo que hace es que valida que al poner el nombre no quede vacio o con espacios
    private String nombre;
    private List<Integer> eventosFacultativosIds;
    public EstudianteDTO(){}//constructor vacio necesario para solo llamar al estudiante

    public EstudianteDTO(Integer id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }

}
