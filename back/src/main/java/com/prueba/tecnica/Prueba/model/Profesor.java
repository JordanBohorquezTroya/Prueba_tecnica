package com.prueba.tecnica.Prueba.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "JJBT_PROFESOR")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROFESOR_SEQ")
    @SequenceGenerator(name = "PROFESOR_SEQ", sequenceName = "PROFESOR_SEQ", allocationSize = 1)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "profesor")
    private List<Calificacion> calificaciones;
}
