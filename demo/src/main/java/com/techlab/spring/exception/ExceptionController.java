package com.techlab.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ProductoNotFoundException.class)
    public ResponseEntity<String> productNotFoundError(ProductoNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error con producto: " + ex.getMessage());
    }

    @ExceptionHandler(ProductoDuplicadoException.class)
    public ResponseEntity<String> productoDuplicadoError(ProductoDuplicadoException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Error: " + ex.getMessage());
    }
}

