package com.iseem.backend.Exceptions;

import lombok.Data;

@Data

public class ErrorResponse {
    private int statusCode;
    private String message;
    private Object body;
    public ErrorResponse (int statusCode, String message){
        this.statusCode = statusCode;
        this.message = message;
    }

    public ErrorResponse (int statusCode, String message, Object body){
        this.statusCode = statusCode;
        this.message = message;
        this.body = body;
    }
}
