package com.example.medical_record_system.dto;

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
public class CreatePatientDto {

    @NotBlank
    private String name;

    @Column(unique = true)
    @Pattern(regexp = "\\d{10}", message = "Invalid ID number")
    @NotBlank(message = "UCN is required")
    private String ucn;

    @Column(unique = true)
    private String username;

    @NotNull(message = "GP ID is required")
    private Long gpId;

    @NotNull(message = "Insurance info is required")
    private Boolean isInsured;

}
