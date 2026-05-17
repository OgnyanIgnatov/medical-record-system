package com.example.medical_record_system.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SickNoteDto {

    @DateTimeFormat
    private Date issuedDate;

    private long daysCount;
}
