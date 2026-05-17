package com.example.medical_record_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VisitDto {

    private long id;

    @DateTimeFormat
    @NotNull
    private Date date;

    @NotBlank
    private String diagnosis;

    @NotBlank
    private String treatment;

    @NotNull
    private float price;
}
