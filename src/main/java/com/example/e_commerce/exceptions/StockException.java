package com.example.e_commerce.exceptions;

public class StockException extends IllegalArgumentException{
    public StockException(String message){
        super(message);
    }
}
