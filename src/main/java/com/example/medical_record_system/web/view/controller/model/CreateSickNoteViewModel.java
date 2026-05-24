package com.example.medical_record_system.web.view.controller.model;

import jakarta.validation.constraints.Min;
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
public class CreateSickNoteViewModel {

    private long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Issue date is required")
    private Date issuedDate;

    @NotNull(message = "Days count is required")
    @Min(value=1, message = "Daus count must be at least 1")
    private Long daysCount;

    @NotNull(message = "Please select visit")
    private Long visitId;
}