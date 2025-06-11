package com.facultad.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ProfesorConEventosDTO {
//aqui se manda a llamar al evento para termianr de relacionarlo con los eventos
    //lo de la relacion debe de queda bien hecho en model
    private Integer id;
    private String nombre;
    private List<EventoFacultativoDTO> eventos; // Reutiliza el DTO de eventos

    public ProfesorConEventosDTO(Integer id, String nombre, List<EventoFacultativoDTO> eventos) {
        this.id = id;
        this.nombre = nombre;
        this.eventos = eventos;
    }

}
