package com.example.medical_record_system.dto;

import jakarta.validation.constraints.DecimalMin;
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date date;

    @NotNull
    private Long patientId;

    @NotNull
    private Long doctorId;

    @NotBlank
    private String diagnosis;

    @NotBlank
    private String treatment;

    @NotNull
    @DecimalMin(value = "0.0", message = "Price cannot be negative")
    private Float price;
}