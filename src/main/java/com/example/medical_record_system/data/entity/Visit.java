package com.example.medical_record_system.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private Date date;

    @ManyToOne
    @NotBlank
    private Patient patient;

    @ManyToOne
    @NotBlank
    private Doctor doctor;

    @NotBlank
    private String diagnosis;

    @NotBlank
    private String treatment;

    @NotBlank
    private float price;

    @OneToOne
    private SickNote sickNote;
}
