package com.prueba.tecnica.Prueba.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final String resource;
    private final String name;
    private final Object fieldValue;

    public ResourceNotFoundException(String name, String resource , Object fieldValue) {
        super();
        this.resource = resource;
        this.name = name;
        this.fieldValue = fieldValue;
    }
}
