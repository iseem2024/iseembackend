package com.iseem.backend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.iseem.backend.Exceptions.AlreadyExistException;
import com.iseem.backend.Exceptions.ErrorResponse;
import com.iseem.backend.Exceptions.NotFoundException;
import com.iseem.backend.Exceptions.RendezVousExistsException;
import com.iseem.backend.Exceptions.WrongDataTypeException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(RendezVousExistsException.class)
    public ResponseEntity<ErrorResponse> handleRendezVousExist(RendezVousExistsException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), ex.getRendezVous());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(WrongDataTypeException.class)
    public ResponseEntity<ErrorResponse> handleDataTyeInput(WrongDataTypeException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    public ResponseEntity<ErrorResponse> handleAlreadyExistException(AlreadyExistException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
    
}
