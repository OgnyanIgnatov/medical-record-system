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
public class DoctorDto {
    private long id;

    @Column(unique = true)
    @Pattern(regexp = "[a-z]{3}-\\d{6}", message = "Invalid uid pattern!")
    @NotBlank(message = "UID is required")
    private String uid;

    @Column(unique = true)
    private String username;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Degree is required")
    private String degree;
}
