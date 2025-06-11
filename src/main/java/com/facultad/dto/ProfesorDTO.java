package com.facultad.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ProfesorDTO {
//datos puestos de igual manera que en el model
    private Integer id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;

    public ProfesorDTO(){}

    public ProfesorDTO(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

}
