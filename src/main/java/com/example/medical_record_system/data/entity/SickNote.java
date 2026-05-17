package com.example.medical_record_system.data.entity;

import jakarta.persistence.Entity;
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
public class SickNote {

    @DateTimeFormat
    @NotBlank
    private Date issuedDate;

    @NotBlank
    private long daysCount;

    @OneToOne
    @NotBlank
    private Visit visit;
}
