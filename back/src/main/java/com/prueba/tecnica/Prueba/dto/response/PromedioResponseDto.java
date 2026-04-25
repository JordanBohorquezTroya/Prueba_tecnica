package com.prueba.tecnica.Prueba.dto.response;


public record PromedioResponseDto(String alumno,
                                  String materia,
                                  double promedio,
                                  String observacion) {
}
