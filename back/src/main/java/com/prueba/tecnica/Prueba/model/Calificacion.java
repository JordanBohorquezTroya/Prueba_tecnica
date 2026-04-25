package com.prueba.tecnica.Prueba.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "JJBT_CALIFICACION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CALIFICACION_SEQ")
    @SequenceGenerator(name = "CALIFICACION_SEQ", sequenceName = "CALIFICACION_SEQ", allocationSize = 1)
    private Long id;
    private String materia;

    private double nota1;
    private double nota2;
    private double nota3;

    @ManyToOne
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;
}
