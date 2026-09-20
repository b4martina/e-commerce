package com.example.e_commerce.exceptions;

public class DuplicateProductError extends RuntimeException{

    public DuplicateProductError(String message){
        super(message);
    }

}
