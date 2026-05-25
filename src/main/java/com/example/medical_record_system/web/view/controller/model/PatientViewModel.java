package com.example.medical_record_system.web.view.controller.model;

import com.example.medical_record_system.data.entity.Doctor;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PatientViewModel {

    private long id;

    @NotBlank
    private String name;

    @Column(unique = true)
    @Pattern(regexp = "\\d{10}", message = "Invalid ID number")
    @NotBlank(message = "UCN is required")
    private String ucn;

    @Column(unique = true)
    private String username;

    @NotNull(message = "Please select GP doctor")
    private Long gpId;

    @NotNull(message = "Insurance info is required")
    private Boolean isInsured;
}
