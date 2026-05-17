package com.example.medical_record_system.web.view.controller.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VisitViewModel {

    @DateTimeFormat
    private Date date;

    @NotBlank
    private String diagnosis;

    @NotBlank
    private String treatment;

    @NotBlank
    private float price;
}
