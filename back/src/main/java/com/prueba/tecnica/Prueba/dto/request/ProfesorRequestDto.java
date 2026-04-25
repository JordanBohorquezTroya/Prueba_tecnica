package com.prueba.tecnica.Prueba.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ProfesorRequestDto(
        @NotBlank(message = "El nombre del profesor no puede ser vacio")
        String nombre
) {
}
