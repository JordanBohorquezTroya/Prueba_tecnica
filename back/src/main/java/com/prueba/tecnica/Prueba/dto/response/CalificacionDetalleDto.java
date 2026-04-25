package com.prueba.tecnica.Prueba.dto.response;

public record CalificacionDetalleDto(
        Long id,
        String materia,
        double nota1,
        double nota2,
        double nota3
) {}