package com.example.medical_record_system.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Pattern;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Patient extends BaseEntity{

    @NotBlank
    private String name;

    @Column(unique = true)
    @Pattern(regexp = "\\d{10}", message = "Invalid ID number")
    @NotBlank
    private String ucn;

    @ManyToOne
    @NotNull
    private Doctor gp;

    @NotNull
    private Boolean isInsured;

    @OneToMany(mappedBy = "patient")
    List<Visit> visits;
}
