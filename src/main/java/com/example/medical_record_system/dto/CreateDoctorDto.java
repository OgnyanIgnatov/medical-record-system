package com.example.medical_record_system.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateDoctorDto {

    @Column(unique = true)
    @Pattern(regexp = "[a-z]{3}-\\d{6}", message = "Invalid uid pattern!")
    @NotBlank
    private String uid;

    @NotBlank
    private String name;

    @NotBlank
    private String degree;
}
