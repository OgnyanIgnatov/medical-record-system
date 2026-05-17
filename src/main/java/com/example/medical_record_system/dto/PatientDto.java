package com.example.medical_record_system.dto;

import com.example.medical_record_system.data.entity.Doctor;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PatientDto {

    private long id;

    @NotBlank
    private String name;

    @Column(unique = true)
    @Pattern(regexp = "\\d{10}", message = "Invalid ID number")
    @NotBlank
    private String ucn;

    @NotNull
    private Long gpId;

    @NotNull
    private Boolean isInsured;

}
