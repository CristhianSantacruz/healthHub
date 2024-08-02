package com.klashz.exceptions;

public class PatientNotExistsException extends RuntimeException{

    public PatientNotExistsException(){
        super("El paciente no existe");
    }
}
