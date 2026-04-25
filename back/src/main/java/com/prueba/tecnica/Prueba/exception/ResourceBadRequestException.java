package com.prueba.tecnica.Prueba.exception;

import lombok.Getter;


@Getter
public class ResourceBadRequestException extends RuntimeException {

    private final String resource;
    private final String name;
    private final Object fieldValue;

    public ResourceBadRequestException(String name, String resource , Object fieldValue) {
        super(String.format("El recurso %s con %s = %s es inválido", resource, name, fieldValue));
        this.resource = resource;
        this.name = name;
        this.fieldValue = fieldValue;
    }
}
