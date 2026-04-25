package com.prueba.tecnica.Prueba.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "JJBT_ALUMNO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ALUMNO_SEQ")
    @SequenceGenerator(name = "ALUMNO_SEQ", sequenceName = "ALUMNO_SEQ", allocationSize = 1)
    private Long id;
    private String nombre;
    private String direccion;
    private int edad;

    @OneToMany(mappedBy = "alumno")
    private List<Calificacion> calificaciones;
}
