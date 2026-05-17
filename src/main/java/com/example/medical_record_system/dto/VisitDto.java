package com.example.medical_record_system.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VisitDto {

    @DateTimeFormat
    private Date date;

    private String diagnosis;

    private String treatment;

    private float price;
}
