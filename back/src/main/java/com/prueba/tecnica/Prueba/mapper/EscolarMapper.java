package com.prueba.tecnica.Prueba.mapper;

import com.prueba.tecnica.Prueba.dto.request.AlumnoRequestDto;
import com.prueba.tecnica.Prueba.dto.request.CalificacionRequestDto;
import com.prueba.tecnica.Prueba.dto.request.ProfesorRequestDto;
import com.prueba.tecnica.Prueba.dto.response.AlumnoResponsetDto;
import com.prueba.tecnica.Prueba.dto.response.CalificacionResponseDto;
import com.prueba.tecnica.Prueba.dto.response.ProfesorResponseDto;
import com.prueba.tecnica.Prueba.model.Alumno;
import com.prueba.tecnica.Prueba.model.Calificacion;
import com.prueba.tecnica.Prueba.model.Profesor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EscolarMapper {

    @Mapping(target = "id", ignore = true)
    Profesor toProfesor (ProfesorRequestDto profesorRequestDto);
    ProfesorResponseDto toProfesorResponseDTO(Profesor profesor);

    @Mapping(target = "id", ignore = true)
    Alumno toAlumno (AlumnoRequestDto profesorRequestDto);
    AlumnoResponsetDto toAlumnoResponseDTO(Alumno profesor);

    @Mapping(target = "profesor", source = "profesor.nombre")
    @Mapping(target = "alumno", source = "alumno.nombre")
    @Mapping(target = "calificacion", source = ".")
    CalificacionResponseDto toCalificacionResponseDTO(Calificacion profesor);
}
