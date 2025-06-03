package com.facultad.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ProfesorConEventosDTO {

    private Integer id;
    private String nombre;
    private List<EventoFacultativoDTO> eventos; // Reutiliza el DTO de eventos

    public ProfesorConEventosDTO(Integer id, String nombre, List<EventoFacultativoDTO> eventos) {
        this.id = id;
        this.nombre = nombre;
        this.eventos = eventos;
    }

}
