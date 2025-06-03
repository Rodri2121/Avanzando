package com.facultad.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class EstudianteConEventosDTO {

    private Integer id;
    private String nombre;
    private List<EventoFacultativoDTO> eventos;

    public EstudianteConEventosDTO(Integer id, String nombre, List<EventoFacultativoDTO> eventos) {
        this.id = id;
        this.nombre = nombre;
        this.eventos = eventos;
    }
}
