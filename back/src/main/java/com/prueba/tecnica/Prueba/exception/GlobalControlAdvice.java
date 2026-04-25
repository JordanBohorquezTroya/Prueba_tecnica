package com.prueba.tecnica.Prueba.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalControlAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException (ResourceNotFoundException ex, WebRequest webRequest){
        log.warn("Recusro no encontrado - Path : {} , Message : {}", webRequest.getDescription(false), ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Recurso no encontrado");
        problemDetail.setType(URI.create("http://localhost/api/v1/not-found"));
        problemDetail.setProperty("TimesTamp", Instant.now());
        problemDetail.setProperty("Resource", ex.getResource());
        problemDetail.setProperty("Campo", ex.getName());
        problemDetail.setProperty("Value", ex.getFieldValue());
        return problemDetail;

    }

    @ExceptionHandler(ResourceBadRequestException.class)
    public ProblemDetail handleBadRequest(ResourceBadRequestException ex, WebRequest webRequest){

        log.warn("Bad request - Path : {} , Message : {}", webRequest.getDescription(false), ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Bad Request");
        problemDetail.setType(URI.create("http://localhost/api/v1/bad-request"));
        problemDetail.setProperty("TimesTamp", Instant.now());
        problemDetail.setProperty("resource", ex.getResource());
        problemDetail.setProperty("field", ex.getName());
        problemDetail.setProperty("value", ex.getFieldValue());
        return problemDetail;
    }

    @ExceptionHandler(ResourceDuplicateException.class)
    public ProblemDetail handleDuplicate(ResourceDuplicateException ex, WebRequest webRequest){

        log.warn("Bad request - Path : {} , Message : {}", webRequest.getDescription(false), ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Bad Request");
        problemDetail.setType(URI.create("http://localhost/api/v1/bad-request"));
        problemDetail.setProperty("TimesTamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleResourceBadRqeustException (MethodArgumentNotValidException ex, WebRequest webRequest){
        log.warn("Recusro no encontrado - Path : {} , Message : {}", webRequest.getDescription(false), ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "La validacion fallo en uno o mas campos");
        problemDetail.setTitle("Validacion");
        problemDetail.setType(URI.create("http://localhost/api/v1/bad-rquest"));
        problemDetail.setProperty("TimesTamp", Instant.now());
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> errorMap.put(fieldError.getField(), fieldError.getDefaultMessage()));
        problemDetail.setProperty("erros", errorMap);
        return problemDetail;

    }


    @ExceptionHandler(Exception.class)
    public ProblemDetail handleResourceException (Exception ex, WebRequest webRequest){
        log.error("A orcurrido un erros inesperado  : {} , Message : {}", webRequest.getDescription(false), ex.getMessage(), ex);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "A orcurrido un erros inesperado");
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setType(URI.create("http://localhost/api/v1/internal-server-error"));
        problemDetail.setProperty("TimesTamp", Instant.now());
        return problemDetail;

    }
}
