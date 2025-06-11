package com.facultad.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class EstudianteConEventosDTO {

    private Integer id; //se declaran los valores en privado
    private String nombre; //nombre del estudiante
    private List<EventoFacultativoDTO> eventos; //se manda a llamar el DTO del evento para poder acceder a este mismo y sus atributos

    public EstudianteConEventosDTO(Integer id, String nombre, List<EventoFacultativoDTO> eventos) { //constructor para poder ver los atributos junto al estudiante
        this.id = id;
        this.nombre = nombre;
        this.eventos = eventos;
    }
}
