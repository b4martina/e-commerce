package com.example.e_commerce.security;


import org.apache.coyote.Response;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String > handleNotFound(ResourceNotFoundException ex){
    return new ResponseEntity<>(
            ex.getMessage(), HttpStatus.NOT_FOUND
    );
}

@ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ia){
    return new ResponseEntity<>(
            ia.getMessage(),HttpStatus.BAD_REQUEST
    );
}
/*
@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodExceptions(MethodArgumentNotValidException men){
   Map<String,String> errors = new HashMap<>();

   men.getBindingResult()
           .getFieldError()
           .forEach(error -> errors.put (error.getField(),
                   error.getDefaultMessage()
           ));
    return new ResponseEntity<>(
            errors,
            HttpStatus.BAD_REQUEST
    );

}*/

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(
                        error.getField(),
                        error.getDefaultMessage()));
        return new ResponseEntity<>(
                errors,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneral (Exception ex){
    return new ResponseEntity<>(
            ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR
    );
}}
