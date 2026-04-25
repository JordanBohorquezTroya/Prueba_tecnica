package com.prueba.tecnica.Prueba.service.impl;

import com.prueba.tecnica.Prueba.dto.request.AlumnoRequestDto;
import com.prueba.tecnica.Prueba.dto.request.CalificacionRequestDto;
import com.prueba.tecnica.Prueba.dto.request.ProfesorRequestDto;
import com.prueba.tecnica.Prueba.dto.response.AlumnoResponsetDto;
import com.prueba.tecnica.Prueba.dto.response.CalificacionResponseDto;
import com.prueba.tecnica.Prueba.dto.response.PromedioResponseDto;
import com.prueba.tecnica.Prueba.dto.response.ProfesorResponseDto;
import com.prueba.tecnica.Prueba.exception.ResourceBadRequestException;
import com.prueba.tecnica.Prueba.exception.ResourceDuplicateException;
import com.prueba.tecnica.Prueba.mapper.EscolarMapper;
import com.prueba.tecnica.Prueba.model.Alumno;
import com.prueba.tecnica.Prueba.model.Calificacion;
import com.prueba.tecnica.Prueba.model.Profesor;
import com.prueba.tecnica.Prueba.repository.AlumnoRepository;
import com.prueba.tecnica.Prueba.repository.CalificacionRepository;
import com.prueba.tecnica.Prueba.repository.ProfesorRepository;
import com.prueba.tecnica.Prueba.service.EscolarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EscolarServiceImpl implements EscolarService {

    private final EscolarMapper escolarMapper;
    private final ProfesorRepository profesorRepository;
    private final AlumnoRepository alumnoRepository;
    private final CalificacionRepository calificacionRepository;

    @Override
    public ProfesorResponseDto crearProfesor(ProfesorRequestDto profesorRequestDto) {
        Profesor profesor = escolarMapper.toProfesor(profesorRequestDto);
        Profesor profesorSave = profesorRepository.save(profesor);
        log.info("Profesor {}, guardado ", profesorSave.getNombre());
        return escolarMapper.toProfesorResponseDTO(profesorSave);
    }

    @Override
    public AlumnoResponsetDto crearAlumno(AlumnoRequestDto alumnoRequestDto) {
        Alumno profesor = escolarMapper.toAlumno(alumnoRequestDto);
        Alumno alumnoSave = alumnoRepository.save(profesor);
        log.info("Alumno {}, guardado ", alumnoSave.getNombre());
        return escolarMapper.toAlumnoResponseDTO(alumnoSave);
    }

    @Override
    public CalificacionResponseDto crearCalificacion(CalificacionRequestDto calificacionRequestDto) {

        Alumno alumno = alumnoRepository.findById(calificacionRequestDto.alumnoId())
                .orElseThrow(() -> new ResourceBadRequestException("Alumno", "id", calificacionRequestDto.alumnoId()));

        Profesor profesor = profesorRepository.findById(calificacionRequestDto.profesorId())
                .orElseThrow(() -> new ResourceBadRequestException("Profesor", "id", calificacionRequestDto.profesorId()));

        if (calificacionRepository.existsByAlumnoIdAndMateria(calificacionRequestDto.alumnoId(), calificacionRequestDto.materia())) {
            throw new ResourceDuplicateException(calificacionRequestDto.materia());
        }

        Calificacion calificacion = Calificacion.builder()
                .alumno(alumno)
                .profesor(profesor)
                .materia(calificacionRequestDto.materia())
                .nota1(calificacionRequestDto.nota1())
                .nota2(calificacionRequestDto.nota2())
                .nota3(calificacionRequestDto.nota3())
                .build();

        Calificacion calificacionSave = calificacionRepository.save(calificacion);
        log.info("Calificacion guardado para el alumno {}, en la materia {} ", alumno.getNombre() , calificacionRequestDto.materia() );
        return escolarMapper.toCalificacionResponseDTO(calificacionSave);

    }

    @Override
    public List<PromedioResponseDto> obtenerCalificaciones() {
        List<PromedioResponseDto> lista = calificacionRepository.findAll().stream().map(
                calificacion -> {
                    double promedio = (calificacion.getNota1() + calificacion.getNota2() + calificacion.getNota3())/ 3;
                    String observacion;

                    if (promedio <= 5.0) {
                        observacion = "Regular";
                    } else if (promedio <= 7.99) {
                        observacion = "Bueno";
                    } else {
                        observacion = "Muy Bueno";
                    }

                    return new PromedioResponseDto(
                            calificacion.getAlumno().getNombre(),
                            calificacion.getMateria(),
                            promedio,
                            observacion
                    );
                }
        ).toList();
        return lista;
    }
}
