package com.example.e_commerce.exceptions;


import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler (OrderNotAvailableException.class)
    public ResponseEntity<Map<String, Object> > handleOrderExceptions (OrderNotAvailableException ex){
    Map <String, Object> error = new HashMap<>();
    error.put("status", 404);
    error.put("error", "Not Found");
    error.put("message",ex.getMessage());

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);}

    @ExceptionHandler(ProductNotAvailableException.class)
    public ResponseEntity<Map<String, Object> > handleProductExceptions (ProductNotAvailableException ex){
        Map <String, Object> error = new HashMap<>();
        error.put("status", 404);
        error.put("error", "Not Found");
        error.put("message",ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
}
    @ExceptionHandler(DuplicateProductError.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateProductErrors(DuplicateProductError ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", 409);
        error.put("error", "Duplicate product");
        error.put("message", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", 400);
        error.put("error", "Bad Request");
        error.put("message", ex.getMessage());

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);}
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorizedException(UnauthorizedException ex){
        Map<String, Object> error = new HashMap<>();
        error.put("status", 401);
        error.put("error", "Unauthorized");
        error.put("message", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(NoPermissionException.class)
    public ResponseEntity<Map<String, Object>> handleNoPermissionException(NoPermissionException ex){
        Map<String, Object> error = new HashMap<>();
        error.put("status", 403);
        error.put("error", "Forbidden");
        error.put("message", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(StockException.class)
    public ResponseEntity<Map<String, Object>> handleStockExceptions(StockException ex){
        Map<String, Object> error = new HashMap<>();
        error.put("status", 400);
        error.put("error", "Bad Request");
        error.put("message", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
