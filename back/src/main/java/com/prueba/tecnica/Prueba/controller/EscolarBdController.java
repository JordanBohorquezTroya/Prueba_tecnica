package com.prueba.tecnica.Prueba.controller;

import com.prueba.tecnica.Prueba.dto.request.AlumnoRequestDto;
import com.prueba.tecnica.Prueba.dto.request.CalificacionRequestDto;
import com.prueba.tecnica.Prueba.dto.request.ProfesorRequestDto;
import com.prueba.tecnica.Prueba.dto.response.AlumnoResponsetDto;
import com.prueba.tecnica.Prueba.dto.response.CalificacionResponseDto;
import com.prueba.tecnica.Prueba.dto.response.ProfesorResponseDto;
import com.prueba.tecnica.Prueba.dto.response.PromedioResponseDto;
import com.prueba.tecnica.Prueba.service.EscolarService;
import com.prueba.tecnica.Prueba.service.EscolarServiceBd;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bd/escolar")
@RequiredArgsConstructor
public class EscolarBdController {

    private final EscolarServiceBd escolarService;

    @PostMapping("/profesor")
    public ResponseEntity<ProfesorResponseDto> crearProfesor (@RequestBody @Valid ProfesorRequestDto profesorRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(escolarService.crearProfesor(profesorRequestDto));
    }

    @PostMapping("/alumno")
    public ResponseEntity<AlumnoResponsetDto> crearAlumno (@RequestBody @Valid AlumnoRequestDto profesorRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(escolarService.crearAlumno(profesorRequestDto));
    }

    @PostMapping("/calificacion")
    public ResponseEntity<CalificacionResponseDto> crearAlumno (@RequestBody @Valid CalificacionRequestDto profesorRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(escolarService.crearCalificacion(profesorRequestDto));
    }

    @GetMapping("/calificacion")
    public ResponseEntity<List<PromedioResponseDto>> obtenerCalificaciones () {
        return ResponseEntity.status(HttpStatus.OK).body(escolarService.obtenerCalificaciones());
    }
}
