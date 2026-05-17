package com.example.medical_record_system.web.view.controller.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateVisitViewModel {

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
