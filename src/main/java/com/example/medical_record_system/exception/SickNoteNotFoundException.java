package com.example.medical_record_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SickNoteNotFoundException extends RuntimeException {

    public SickNoteNotFoundException(String message) {
        super(message);
    }
}