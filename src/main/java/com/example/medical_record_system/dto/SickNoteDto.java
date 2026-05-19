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
public class SickNoteDto {

    private long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date issuedDate;

    @NotNull
    private Long daysCount;

    @NotNull
    private Long visitId;
}