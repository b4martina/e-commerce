package com.example.e_commerce.exceptions;

public class NoPermissionException extends RuntimeException{
    public NoPermissionException(String message ){
        super(message);
    }
}
