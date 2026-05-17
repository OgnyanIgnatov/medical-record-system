package com.example.medical_record_system.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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
    private Date date;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

    private String diagnosis;

    private String treatment;

    private float price;
}
