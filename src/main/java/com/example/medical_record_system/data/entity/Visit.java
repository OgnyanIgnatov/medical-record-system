package com.example.medical_record_system.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Visit extends BaseEntity{

    @DateTimeFormat
    @NotNull
    private Date date;

    @ManyToOne
    @NotNull
    private Patient patient;

    @ManyToOne
    @NotNull
    private Doctor doctor;

    @NotBlank
    private String diagnosis;

    @NotBlank
    private String treatment;

    @NotNull
    private float price;

    @OneToOne
    private SickNote sickNote;
}
