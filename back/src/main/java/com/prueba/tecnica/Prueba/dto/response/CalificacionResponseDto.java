package com.prueba.tecnica.Prueba.dto.response;




public record CalificacionResponseDto(
                                      String alumno,
                                      String profesor,
                                      CalificacionDetalleDto calificacion) {
}
