package com.example.medical_record_system.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateSickNoteDto {

    @DateTimeFormat
    @NotNull
    private Date issuedDate;

    @NotNull
    private long daysCount;
}
