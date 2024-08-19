package com.iseem.backend.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.iseem.backend.Entities.RendezVous;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RendezVousExistsException extends RuntimeException {

    private RendezVous rendezVous;
    public RendezVousExistsException(String message, RendezVous rendezVous) {
        super(message);
        this.rendezVous = rendezVous;
    }

    public RendezVous getRendezVous(){
        return this.rendezVous;
    }

    public void setRendezVous(RendezVous rendezVous){
        this.rendezVous = rendezVous;
    }
}
