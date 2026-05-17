package com.example.medical_record_system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SickNoteDto {

    private long id;

    @DateTimeFormat
    @NotBlank
    private Date issuedDate;

    @NotBlank
    private long daysCount;
}
