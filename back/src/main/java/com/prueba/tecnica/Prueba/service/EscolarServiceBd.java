package com.prueba.tecnica.Prueba.service;

import com.prueba.tecnica.Prueba.dto.request.AlumnoRequestDto;
import com.prueba.tecnica.Prueba.dto.request.CalificacionRequestDto;
import com.prueba.tecnica.Prueba.dto.request.ProfesorRequestDto;
import com.prueba.tecnica.Prueba.dto.response.AlumnoResponsetDto;
import com.prueba.tecnica.Prueba.dto.response.CalificacionResponseDto;
import com.prueba.tecnica.Prueba.dto.response.ProfesorResponseDto;
import com.prueba.tecnica.Prueba.dto.response.PromedioResponseDto;

import java.util.List;

public interface EscolarServiceBd {
    ProfesorResponseDto crearProfesor (ProfesorRequestDto profesorRequestDto);
    AlumnoResponsetDto crearAlumno (AlumnoRequestDto alumnoRequestDto);
    CalificacionResponseDto crearCalificacion(CalificacionRequestDto calificacionRequestDto);
    List<PromedioResponseDto> obtenerCalificaciones();
}
