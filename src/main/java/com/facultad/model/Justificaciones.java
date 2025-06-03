package com.facultad.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Justificaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer justificacio_id;

    private String motivo;

    private String descripcion;

    @OneToMany(mappedBy = "justificaciones", cascade = CascadeType.ALL)
    private List<Estudiante> estudiantes;
}
