package com.example.medical_record_system.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler({
            DoctorNotFoundException.class,
            PatientNotFoundException.class,
            VisitNotFoundException.class,
            SickNoteNotFoundException.class
    })
    public String handleNotFoundException(RuntimeException exception, Model model) {
        model.addAttribute("message", exception.getMessage());
        return "errors/not-found-errors";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception exception, Model model) {
        model.addAttribute("message", exception.getMessage());
        return "errors/errors";
    }
}