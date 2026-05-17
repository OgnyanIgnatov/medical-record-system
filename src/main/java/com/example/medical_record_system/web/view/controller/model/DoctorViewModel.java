package com.example.medical_record_system.web.view.controller.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DoctorViewModel {

    @Column(unique = true)
    @Pattern(regexp = "\\[a-z]{3}-d{6}", message = "Invalid uid pattern!")
    @NotBlank
    private long uid;

    @NotBlank
    private String name;

    @NotBlank
    private String degree;

}
