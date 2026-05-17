package com.example.medical_record_system.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Doctor extends BaseEntity{

    @Column(unique = true)
    @Pattern(regexp = "\\[a-z]{3}-d{6}", message = "Invalid uid pattern!")
    private long uid;

    private String name;

    private String degree;

    @OneToMany(mappedBy = "gp")
    private List<Patient> patients;

    @OneToMany(mappedBy = "doctor")
    private List<Visit> visits;
}
