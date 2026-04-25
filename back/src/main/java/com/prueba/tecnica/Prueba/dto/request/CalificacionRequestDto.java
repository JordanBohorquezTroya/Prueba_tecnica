package com.prueba.tecnica.Prueba.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CalificacionRequestDto(
        @NotNull(message = "La id del alumno no puede ser vacio")
        Long alumnoId,
        @NotNull(message = "La id del profesor no puede ser vacio")
        Long profesorId,
        @NotBlank(message = "La materia del alumno no puede ser vacio")
        String materia,
        @NotNull(message = "La nota1 del alumno no puede ser vacio")
        double nota1,
        @NotNull(message = "La nota2 del alumno no puede ser vacio")
        double nota2,
        @NotNull(message = "La nota3 del alumno no puede ser vacio")
        double nota3) {
}
