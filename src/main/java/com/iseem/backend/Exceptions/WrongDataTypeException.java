package com.iseem.backend.Exceptions;

public class WrongDataTypeException extends RuntimeException {
    public WrongDataTypeException(String message){
        super(message);
    }
    
}
