package com.example.medical_record_system.web.view.controller.model;

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
    @NotNull
    private Date issuedDate;

    @NotNull
    private Long daysCount;

    @NotNull(message = "Please select visit")
    private Long visitId;
}