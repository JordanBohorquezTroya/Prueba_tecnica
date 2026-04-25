package com.prueba.tecnica.Prueba.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record AlumnoRequestDto(
        @NotBlank(message = "El nombre del alumno no puede ser vacio")
        String nombre,
        String direccion,
        @NotNull(message = "La edad es obligatorio")
        @Positive(message = "La edad no debe ser menor que a cero")
        int edad) {
}
