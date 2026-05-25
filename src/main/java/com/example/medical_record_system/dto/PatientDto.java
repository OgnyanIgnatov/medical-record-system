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

    @NotBlank(message = "Name is required")
    private String name;

    @Column(unique = true)
    @Pattern(regexp = "\\d{10}", message = "Invalid ID number")
    @NotBlank(message = "UCN is required")
    private String ucn;

    @Column(unique = true)
    private String username;

    @NotNull(message = "Select GP doctor")
    private Long gpId;

    @NotNull(message = "Select insurance status")
    private Boolean isInsured;

}
