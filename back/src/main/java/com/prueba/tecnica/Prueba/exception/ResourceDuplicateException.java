package com.prueba.tecnica.Prueba.exception;

public class ResourceDuplicateException extends RuntimeException {

    public ResourceDuplicateException(String message) {
        super(String.format("El alumno ya tiene una calificación registrada para la materia %s", message));
    }
}
